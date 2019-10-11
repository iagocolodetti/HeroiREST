package br.com.iagocolodetti.heroi;

import br.com.iagocolodetti.heroi.model.Heroi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author iagocolodetti
 */
public class Util {

    public static String toJson(Object object) {
        return new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create()
                    .toJson(object);
    }
    
    public static Heroi heroiFromJson(String json) throws JsonSyntaxException {
        return new Gson().fromJson(json, new TypeToken<Heroi>(){}.getType());
    }
}
