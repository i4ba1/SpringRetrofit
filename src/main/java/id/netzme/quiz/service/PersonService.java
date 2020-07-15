package id.netzme.quiz.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import id.netzme.quiz.dto.PersonDto;
import id.netzme.quiz.model.Results;
import id.netzme.quiz.model.ResultsItem;
import id.netzme.quiz.repository.IPersonRepository;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

import static okhttp3.OkHttpClient.*;

@Service
public class PersonService implements IPersonService {

    private static final String BASE_URL = "https://randomuser.me/";

    private final IPersonRepository userRepository;

    public PersonService() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getInterceptor())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.userRepository = retrofit.create(IPersonRepository.class);
    }

    @Override
    public PersonDto getPersonDetail() {
        Call<Results> call = userRepository.getPerson();
        Response<Results> response;
        PersonDto dto = null;

        try {
            response = call.execute();
            if (!response.isSuccessful()) {
                throw new IOException(response.errorBody() != null ? response.errorBody().toString():"Unknown error");
            }

            Results results = response.body();
            List<ResultsItem> resultsItems = null;
            if (results != null) {
                resultsItems = results.getResults();
                dto = new PersonDto();
                for (ResultsItem item:resultsItems) {
                    String fullName = item.getName().getTitle()+" "+item.getName().getFirst()+" "+item.getName().getLast();
                    String address  = item.getLocation().getStreet().getNumber()+" "+item.getLocation().getStreet().getName()+" "+item.getLocation().getCity();
                    String pictUrl  = item.getPicture().getLarge();

                    dto.setGender(item.getGender());
                    dto.setFullName(fullName);
                    dto.setAddress(address);
                    dto.setPictUrl(pictUrl);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dto;
    }

    private OkHttpClient getInterceptor(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        return new Builder().addInterceptor(logging).build();
    }
}
