package tec.bd.repository;

public class Queries {

        /**
         * Queries for Movies
         */
        public static final String MOVIES_FIND_ALL_QUERY = "select * from MOVIE";
        public static final String MOVIES_FIND_BY_ID_QUERY = "select m.id, m.title, m.release_date, m.category_id, m.units_available, c.category_name "
                        +
                        "from MOVIE as m join category as c on m.category_id = c.id where m.id = ?";
        public static final String MOVIES_INSERT_QUERY = "insert into MOVIE(title, release_date, category_id, units_available) values (?, ?, ?, ?)";

        public static final String MOVIES_DELETE_MOVIE_ID = "delete from MOVIE where id = ?";

        public static final String MOVIES_UPDATE_MOVIE = "update MOVIE set title = ?, release_date = ?, units_available = ?, category_id = ? where id = ?";

        public static final String CATEGORY_FIND_BY_ID_QUERY = "select id, category_name, category_description from CATEGORY where id = ?";

        public static final String CATEGORY_FIND_ALL_QUERY = "select * from CATEGORY";

        public static final String CATEGORY_INSERT_QUERY = "insert into CATEGORY(category_name, category_description) values (?, ?)";

        public static final String CATEGORY_DELETE_CATEGORY_ID = "delete from CATEGORY where id = ?";

        public static final String CATEGORY_UPDATE_CATEGORY = "update CATEGORY set category_name = ?, category_description = ?";

        public static final String CLIENTE_FIND_BY_ID_QUERY = "select id, client_name, lastname, email, phone_number from CLIENTE where id = ?";

        public static final String CLIENTE_FIND_ALL_QUERY = "select * from CLIENTE";

        public static final String CLIENTE_INSERT_QUERY = "insert into CLIENTE(client_name, lastname, email, phone_number, activity) values (?, ?, ?, ?, ?)";

        public static final String CLIENTE_DELETE_CLIENT_ID = "delete from CLIENTE where id = ?";

        public static final String CLIENTE_UPDATE_CLIENT = "update CLIENTE set client_name = ?, lastname = ?, email = ?, phone_number = ?, activity = ?";

        public static final String RENTALS_FIND_BY_ID_QUERY = "select id, rental_date, client_id, movie_id from RENTALS where id = ?";

        public static final String RENTALS_FIND_ALL_QUERY = "select * from RENTALS";

        public static final String RENTALS_INSERT_QUERY = "insert into RENTALS(rental_date, client_id, movie_id) values (?, ?, ?)";

        public static final String RENTALS_DELETE_RENTALS_ID = "delete from RENTALS where id = ?";

        public static final String RENTALS_UPDATE_RENTALS = "update RENTALS set rental_date = ?, client_id = ?, movie_id = ?";

        public static final String REVIEW_FIND_BY_ID_QUERY = "select id, rating, review_text, created_on, client_id, movie_id from REVIEW where id = ?";

        public static final String REVIEW_FIND_ALL_QUERY = "select * from REVIEW";

        public static final String REVIEW_INSERT_QUERY = "insert into REVIEW(rating, review_text, created_on, client_id, movie_id) values (?, ?, ?, ?, ?)";

        public static final String REVIEW_DELETE_REVIEW_ID = "delete from REVIEW where id = ?";

        public static final String REVIEW_UPDATE_REVIEW = "update REVIEW set rating = ?, review_text = ?, created_on = ?, client_id = ?, movie_id = ?";

        public static final String BLOCKBUSTERLOG_REQUEST_QUERY = "select * from BLOCKBUSTER_LOG order by created_on desc_limit ?";
}
