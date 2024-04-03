package com.kdu.ibebackend.constants.graphql;

public class GraphQLMutations {
    public static String dummyMutation = "mutation MyMutation($guestName: String = \"Asish Mahapatra\") { updateGuest(where: {guest_id: 2}, data: {guest_name: $guestName}) { guest_id guest_name } }";
}
