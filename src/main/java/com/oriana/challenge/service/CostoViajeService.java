package com.oriana.challenge.service;

import com.oriana.challenge.dto.RutaMinimaResponse;
import com.oriana.challenge.entity.CostoViaje;
import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.repository.CostoViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CostoViajeService {

    @Autowired
    private CostoViajeRepository costoViajeRepository;

    private record Edge(Long to, int cost) {}
    private record Node(Long id, int cost) {}

    public List<CostoViaje> getAllCostoViaje() {
        return costoViajeRepository.findAll();
    }


    public CostoViaje saveCostoViaje(CostoViaje costoViaje) {
        return costoViajeRepository.save(costoViaje);
    }

    public CostoViaje createCostoViaje(CostoViaje costoViaje) throws Exception {
        if (costoViaje.getPuntoOrigen() == null || costoViaje.getPuntoDestino() == null) {
            throw new IllegalArgumentException("Los puntos de venta no pueden ser nulos");
        }
        if (costoViaje.getPuntoOrigen().equals(costoViaje.getPuntoDestino())) {
            throw new Exception("Puntos de venta identicos");
        }

        normalizarOrden(costoViaje);
        return costoViajeRepository.save(costoViaje);
    }


    public List<CostoViaje> getCostosPorPuntoVenta(Long id){
        return costoViajeRepository.findAllByPuntoVenta(id);
    }



    public void deleteCostoViaje(Long puntoA, Long puntoB) {

        if (puntoA == null || puntoB == null) {
            throw new IllegalArgumentException("Los puntos no pueden ser nulos");
        }

        if (puntoA.equals(puntoB)) {
            throw new IllegalArgumentException("Los puntos no pueden ser iguales");
        }

        Long origenId = Math.min(puntoA, puntoB);
        Long destinoId = Math.max(puntoA, puntoB);

        int deleted = costoViajeRepository.deleteByOrigenAndDestino(origenId, destinoId);

        if (deleted == 0) {
            throw new IllegalStateException(
                    "No existe un costo entre esos puntos de venta"
            );
        }

    }

    public RutaMinimaResponse calcularRutaMinima(Long origenId, Long destinoId) {

        Map<Long, List<Edge>> graph = buildGraph();

        PriorityQueue<Node> pq =
                new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));

        Map<Long, Integer> dist = new HashMap<>();
        Map<Long, Long> previous = new HashMap<>();

        pq.add(new Node(origenId, 0));
        dist.put(origenId, 0);

        while (!pq.isEmpty()) {

            Node current = pq.poll();

            if (current.cost > dist.getOrDefault(current.id, Integer.MAX_VALUE)) {
                continue;
            }

            for (Edge edge : graph.getOrDefault(current.id, List.of())) {

                int newCost = current.cost + edge.cost();

                if (newCost < dist.getOrDefault(edge.to(), Integer.MAX_VALUE)) {
                    dist.put(edge.to(), newCost);
                    previous.put(edge.to(), current.id); // 👈 store previous
                    pq.add(new Node(edge.to(), newCost));
                }
            }
        }

        if (!dist.containsKey(destinoId)) {
            throw new IllegalStateException("No existe ruta entre los puntos");
        }

        List<Long> path = reconstruirCamino(previous, origenId, destinoId);

        return new RutaMinimaResponse(dist.get(destinoId), path);
    }



    private List<Long> reconstruirCamino(Map<Long, Long> previous,
        Long origenId,
        Long destinoId) {

            LinkedList<Long> path = new LinkedList<>();
            Long current = destinoId;

            while (current != null) {
                path.addFirst(current);
                current = previous.get(current);
            }

            if (!path.getFirst().equals(origenId)) {
                throw new IllegalStateException("Ruta inválida");
            }

            return path;
    }




    private Map<Long, List<Edge>> buildGraph() {
        List<CostoViaje> rutas = costoViajeRepository.findAll();
        Map<Long, List<Edge>> graph = new HashMap<>();

        for (CostoViaje ruta : rutas) {

            Long origen = ruta.getPuntoOrigen().getId();
            Long destino = ruta.getPuntoDestino().getId();
            int costo = ruta.getCosto();

            graph.computeIfAbsent(origen, k -> new ArrayList<>())
                    .add(new Edge(destino, costo));

            graph.computeIfAbsent(destino, k -> new ArrayList<>())
                    .add(new Edge(origen, costo));
        }

        return graph;
    }




    //Utiles
    private void normalizarOrden(CostoViaje costoViaje){
        // Normalizar el orden (min → max)
        PuntoVenta origen = costoViaje.getPuntoOrigen();
        PuntoVenta destino = costoViaje.getPuntoDestino();

        if (origen.getId() > destino.getId()) {
            costoViaje.setPuntoOrigen(destino);
            costoViaje.setPuntoDestino(origen);
        }
    }

}




