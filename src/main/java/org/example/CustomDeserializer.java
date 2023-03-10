package org.example;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CustomDeserializer extends StdDeserializer<Person> {

    CustomDeserializer(){
        this(null);
    }
    protected CustomDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Person deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        Person person=new Person();
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        person.setId(node.get(VarNames.ID).asInt());
        person.setName(node.get(VarNames.NAME).asText());
        person.setEmail(node.get(VarNames.EMAIL).asText());
        JsonNode nodeList = node.get(VarNames.ADDRESS_LIST);

        loadList(nodeList,person);

        return person;
    }

    private void loadList(JsonNode nodeList,Person person){
        if(nodeList.isArray()){
            for(JsonNode n:nodeList){

                Address address=new Address();
                address.setId(n.get(VarNames.ADDRESS_ID).asInt());
                address.setCity(n.get(VarNames.ADDRESS_CITY).asText());
                address.setState(n.get(VarNames.ADDRESS_STATE).asText());
                address.setCountry(n.get(VarNames.ADDRESS_COUNTRY).asText());

                person.getAddressList().add(address);
            }

        }
    }
}
