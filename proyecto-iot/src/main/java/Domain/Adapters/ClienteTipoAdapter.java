package Domain.Adapters;

import Domain.Entities.Publisher;
import Domain.Entities.Subscriber;
import Domain.Entities.TipoCliente;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;


public class ClienteTipoAdapter extends TypeAdapter<TipoCliente> {

    @Override
    public void write(JsonWriter jsonWriter, TipoCliente tipoCliente) throws IOException {

    }

    @Override
    public TipoCliente read(JsonReader jsonReader) throws IOException {
        JsonToken token = jsonReader.peek();

        if(token.equals(JsonToken.STRING)) {
            String tipoClienteS = jsonReader.nextString();
            TipoCliente tipoCliente = tipoClienteFromString(tipoClienteS);
            return tipoCliente;
        }
        return null;
    }
    public TipoCliente tipoClienteFromString(String string) {
        TipoCliente tipo = null;

        switch (string) {
            case "Subscriber": tipo = new Subscriber(); break;
            case "Publisher": tipo = new Publisher(); break;
        }

        return tipo;
    }
}
