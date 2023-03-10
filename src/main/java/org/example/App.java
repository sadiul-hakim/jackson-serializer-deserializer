package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final ObjectMapper mapper=new ObjectMapper();
    public static void main( String[] args ) throws JsonProcessingException {

        //person
        Person person1=new Person();
        person1.setId(1);
        person1.setName("Hakim");
        person1.setEmail("hakim@gmail.com");

        Address address1=new Address();
        address1.setId(1);
        address1.setCity("Kushtia");
        address1.setState("Khulna");
        address1.setCountry("Bangladesh");

        Address address2=new Address();
        address2.setId(2);
        address2.setCity("Khumarkhali");
        address2.setState("Khulna");
        address2.setCountry("Bangladesh");

        person1.getAddressList().add(address1);
        person1.getAddressList().add(address2);

        //custom serializer
        SimpleModule module=new SimpleModule("CustomeSerializer",new Version(1,0,0,null,null,null));
        module.addSerializer(Person.class,new CustomeSerializer());
        mapper.registerModule(module);
        String jsonByCustomSerializer;
        try {
            jsonByCustomSerializer=mapper.writeValueAsString(person1);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println(jsonByCustomSerializer);

        //custom deserializer
        SimpleModule module1=new SimpleModule("CustomDeserializer",new Version(1,0,0,null,null,null));
        module1.addDeserializer(Person.class,new CustomDeserializer());
        mapper.registerModule(module1);

        Person personFromCustomDeserializer= mapper.readValue(jsonByCustomSerializer, Person.class);

        System.out.println(personFromCustomDeserializer);
    }

    //writing object to a json file as json string
    private static void writeToTarget(Object person){
        try {
            mapper.writeValue(new File("D:\\java_code\\Practise\\src\\main\\java\\org\\example\\target.json"),person);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //converting object to json string
    private static String writeValueToString(Object person){
        String json;
        try {
           json=mapper.writeValueAsString(person);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    //converting Collection to json string
    private static String writeArrayToString(List<Address> addresses){
        String json;
        try {
            json=mapper.writeValueAsString(addresses);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    //converting json to Collection
    private static List<Address> readListFromJson(String json){
        List<Address> addresses;
        try {
            addresses=mapper.readValue(json, new TypeReference<List<Address>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return addresses;
    }

    //reading json from file and converting to object
    private static Person readFromTarget(){
        Person person;
        try {
            person=mapper.readValue(new File("D:\\java_code\\Practise\\src\\main\\java\\org\\example\\target.json"),Person.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    //converting json to object
    private static Person readFromString(String json){
        Person person;
        try {
            person=mapper.readValue(json,Person.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    //converting json to object reading properties one by one
    private static Person readAsNode(String json){
        Person person=new Person();

        try {
            JsonNode node=mapper.readTree(json);
            person.setId(node.get("id").asInt());
            person.setName(node.get("name").asText());
            person.setEmail(node.get("email").asText());

            //Load address
            JsonNode addressList = node.get("addressList");
            loadAddressList(addressList,person);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return person;
    }

    //readAsNode's helper method
    private static void loadAddressList(JsonNode node,Person person){
        if(node.isArray()){
            node.forEach(n->{
                Address address=new Address();
                address.setId(n.get("is").asInt());
                address.setCity(n.get("city").asText());
                address.setState(n.get("state").asText());
                address.setCountry(n.get("country").asText());

                person.getAddressList().add(address);
            });
        }
    }

    private static Map<String,Object> getMap(String json){
        Map<String,Object> map;
        try {
             map=mapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

}
