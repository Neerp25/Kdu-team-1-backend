package com.kdu.ibebackend.controller;

import com.kdu.ibebackend.entities.Room;
import com.kdu.ibebackend.entities.Tenant;
import com.kdu.ibebackend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TestController {

    @Autowired
    private final RoomRepository roomRepository;

    public TestController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/test")
    public String testHealthEndpoint() {
        return "Hey there!! The server works great 👍";
    }

    @QueryMapping
    public Room roomById(@Argument Long id) {
        Optional<Room> roomRes = roomRepository.findById(id);

        if(roomRes.isPresent()) {
            return roomRepository.findById(id).get();
        }

        return null;
    }

//    @SchemaMapping
//    public Tenant tenant(Room room) {
//        return Tenant.getById(room.tenantId());
//    }
}
