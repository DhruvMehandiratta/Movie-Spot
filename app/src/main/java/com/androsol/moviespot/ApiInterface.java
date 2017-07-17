package com.androsol.moviespot;

import com.androsol.moviespot.MovieStructure.CastResults;
import com.androsol.moviespot.MovieStructure.CrewResults;
import com.androsol.moviespot.MovieStructure.Movie;
import com.androsol.moviespot.MovieStructure.MovieDetails;
import com.androsol.moviespot.MovieStructure.MovieVideos;
import com.androsol.moviespot.MovieStructure.Results;
import com.androsol.moviespot.MovieStructure.ReviewResults;
import com.androsol.moviespot.People.PeopleDetails;
import com.androsol.moviespot.People.PeopleImagesArray;
import com.androsol.moviespot.People.PeopleMovieResults;
import com.androsol.moviespot.People.PeopleResults;
import com.androsol.moviespot.People.PeopleTVResults;
import com.androsol.moviespot.TVStructure.TV;
import com.androsol.moviespot.TVStructure.TVCastResults;
import com.androsol.moviespot.TVStructure.TVDetails;
import com.androsol.moviespot.TVStructure.TVResults;
import com.androsol.moviespot.TVStructure.TVSeason;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Dhruv on 23-03-2017.
 */
public interface ApiInterface {

 String postURL = Constants.getLanguageUrl();

    //@Headers("api_key:41baf4a3e5c2e350d87b92554f894f2e")
    String language = Constants.LANGUAGE;

    // MOVIES CALLS...............................

   @GET("movie/popular?api_key=41baf4a3e5c2e350d87b92554f894f2e")
    Call<Results> getItems(@Query("language") String language);

    @GET("movie/upcoming?api_key=41baf4a3e5c2e350d87b92554f894f2e")
    Call<Results> getUpcoming(@Query("language") String language, @Query("page") int page);

    @GET("movie/latest?api_key=41baf4a3e5c2e350d87b92554f894f2e")
    Call<Movie> getLatest(@Query("language") String language);

    @GET("movie/popular?api_key=41baf4a3e5c2e350d87b92554f894f2e")
    Call<Results> getPopular(@Query("language") String language, @Query("page") int page);

    @GET("movie/top_rated?api_key=41baf4a3e5c2e350d87b92554f894f2e")
    Call<Results> getTopRated(@Query("language") String language, @Query("page") int page);

    @GET("movie/now_playing?api_key=41baf4a3e5c2e350d87b92554f894f2e")
    Call<Results> getInTheaters(@Query("language") String language, @Query("page") int page);

         //search movie
    @GET("search/movie?api_key=41baf4a3e5c2e350d87b92554f894f2e")
    Call<Results> getMovieSearchResults(@Query("query") String query, @Query("page") int i);

    // details activity requests

    @GET("movie/{movie_id}?api_key=41baf4a3e5c2e350d87b92554f894f2e")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") long movie_id);

    @GET("movie/{movie_id}/similar?api_key=41baf4a3e5c2e350d87b92554f894f2e")
    Call<Results> getSimilarMovies(@Path("movie_id") long movie_id);

   @GET("movie/{movie_id}/videos?api_key=41baf4a3e5c2e350d87b92554f894f2e&language=en-US")
   Call<MovieVideos> getVideos(@Path("movie_id") long movie_id);

    @GET("movie/{movie_id}/reviews?api_key=41baf4a3e5c2e350d87b92554f894f2e&language=en-US")
    Call<ReviewResults> getReviews(@Path("movie_id") long movie_id);

    @GET("movie/{movie_id}/credits?api_key=41baf4a3e5c2e350d87b92554f894f2e")
    Call<CastResults> getCast(@Path("movie_id") long movie_id);

    @GET("movie/{movie_id}/credits?api_key=41baf4a3e5c2e350d87b92554f894f2e")
    Call<CrewResults> getCrew(@Path("movie_id") long movie_id);


   // TV CALLS......................

 @GET("tv/latest?api_key=41baf4a3e5c2e350d87b92554f894f2e")
 Call<TV> getTVLatest(@Query("language") String language);

 @GET("tv/top_rated?api_key=41baf4a3e5c2e350d87b92554f894f2e")
 Call<TVResults> getTVTopRated(@Query("language") String language, @Query("page") int page);

 @GET("tv/popular?api_key=41baf4a3e5c2e350d87b92554f894f2e")
 Call<TVResults> getTVPopular(@Query("language") String language, @Query("page") int page);

 @GET("tv/airing_today?api_key=41baf4a3e5c2e350d87b92554f894f2e")
 Call<TVResults> getTVToday(@Query("language") String language, @Query("page") int page);

 @GET("tv/on_the_air?api_key=41baf4a3e5c2e350d87b92554f894f2e")
 Call<TVResults> getTVThisWeek(@Query("language") String language, @Query("page") int page);

 @GET("tv/{tv_id}/videos?api_key=41baf4a3e5c2e350d87b92554f894f2e&language=en-US")
 Call<MovieVideos> getTVVideos(@Path("tv_id") long tv_id);

 @GET("tv/{tv_id}?api_key=41baf4a3e5c2e350d87b92554f894f2e")
 Call<TVDetails> getTVDetails(@Path("tv_id") long tv_id);

 @GET("tv/{tv_id}/similar?api_key=41baf4a3e5c2e350d87b92554f894f2e")
 Call<TVResults> getSimilarTV(@Path("tv_id") long tv_id);

 @GET("tv/{tv_id}/credits?api_key=41baf4a3e5c2e350d87b92554f894f2e")
 Call<TVCastResults> getTVCast(@Path("tv_id") long tv_id);

      // search for tv
      @GET("search/tv?api_key=41baf4a3e5c2e350d87b92554f894f2e")
      Call<TVResults> getTVSearchResults(@Query("query") String query, @Query("page") int i);

 //tv season
 @GET("tv/{tv_id}/season/{season_number}?api_key=41baf4a3e5c2e350d87b92554f894f2e")
 Call<TVSeason> getTVSeason(@Path("tv_id") long tv_id, @Path("season_number") long season_number);

//People
 @GET("person/popular?api_key=41baf4a3e5c2e350d87b92554f894f2e")
 Call<PeopleResults> getPeople(@Query("page") int i);

 @GET("search/person?api_key=41baf4a3e5c2e350d87b92554f894f2e")
 Call<PeopleResults> getPeopleSearchResults(@Query("query") String query, @Query("page") int i);

 @GET("person/{person_id}?api_key=41baf4a3e5c2e350d87b92554f894f2e")
 Call<PeopleDetails> getPeopleDetails(@Path("person_id") long person_id);

 @GET("person/{person_id}/images?api_key=41baf4a3e5c2e350d87b92554f894f2e")
 Call<PeopleImagesArray> getPeopleImages(@Path("person_id") long person_id);

 @GET("person/{person_id}/movie_credits?api_key=41baf4a3e5c2e350d87b92554f894f2e")
 Call<PeopleMovieResults> getPeopleMovies(@Path("person_id") long person_id);

 @GET("person/{person_id}/tv_credits?api_key=41baf4a3e5c2e350d87b92554f894f2e")
 Call<PeopleTVResults> getPeopleTV(@Path("person_id") long person_id);

}
