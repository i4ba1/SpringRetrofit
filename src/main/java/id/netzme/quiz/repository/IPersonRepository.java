package id.netzme.quiz.repository;

import id.netzme.quiz.model.Results;
import org.springframework.stereotype.Repository;
import retrofit2.Call;
import retrofit2.http.GET;

@Repository
public interface IPersonRepository {
    @GET("api/")
    Call<Results> getPerson();
}
