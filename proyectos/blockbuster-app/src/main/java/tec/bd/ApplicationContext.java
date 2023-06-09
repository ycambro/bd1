package tec.bd;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import tec.bd.repository.CategoryRepository;
import tec.bd.repository.CategoryRepositoryImpl;
import tec.bd.repository.ClientRepository;
import tec.bd.repository.ClientRepositoryImpl;
import tec.bd.repository.ReviewRepository;
import tec.bd.repository.ReviewRepositoryImpl;
import tec.bd.repository.RentalsRepository;
import tec.bd.repository.RentalsRepositoryImpl;
import tec.bd.repository.MovieRepository;
import tec.bd.repository.MovieRepositoryImpl;
import tec.bd.repository.BlockbusterLogRepository;
import tec.bd.repository.BlockbusterLogRepositoryImpl;
import tec.bd.services.CategoryService;
import tec.bd.services.CategoryServiceImpl;
import tec.bd.services.ClientService;
import tec.bd.services.ClientServiceImpl;
import tec.bd.services.ReviewService;
import tec.bd.services.ReviewServiceImpl;
import tec.bd.services.RentalsService;
import tec.bd.services.RentalsServiceImpl;
import tec.bd.services.MovieService;
import tec.bd.services.MovieServiceImpl;
import tec.bd.services.BlockbusterLogService;
import tec.bd.services.BlockbusterLogServiceImpl;
public class ApplicationContext {

    public CategoryRepository categoryRepository;
    public MovieRepository movieRepository;
    public ClientRepository clientRepository;
    public ReviewRepository reviewRepository;
    public RentalsRepository rentalsRepository;
    public BlockbusterLogRepository blockBusterLogRepository;
    public MovieService movieService;
    public CategoryService categoryService;
    public ClientService clientService;
    public ReviewService reviewService;
    public RentalsService rentalsService;
    public BlockbusterLogService blockbusterLogService;

    private ApplicationContext() {

    }

    public static ApplicationContext init() {
        ApplicationContext appContext = new ApplicationContext();

        var hikariConfig = initHikariDataSource();

        appContext.categoryRepository = initCategoryRepository(hikariConfig);
        appContext.movieRepository = initMovieRepository(hikariConfig);
        appContext.blockBusterLogRepository = initBlockbusterLogRepository(hikariConfig);
        appContext.clientRepository = initClientRepository(hikariConfig);
        appContext.reviewRepository = initReviewRepository(hikariConfig);
        appContext.rentalsRepository = initRentalsRepository(hikariConfig);
        appContext.movieService = initMovieService(appContext.movieRepository, appContext.categoryRepository);
        appContext.categoryService = initCategoryService(appContext.categoryRepository);
        appContext.clientService = initClientService(appContext.clientRepository);
        appContext.reviewService = initReviewService(appContext.reviewRepository, appContext.clientRepository, appContext.movieRepository);
        appContext.rentalsService = initRentalsService(appContext.rentalsRepository, appContext.clientRepository, appContext.movieRepository);
        appContext.blockbusterLogService = initBlockbusterLogService(appContext.blockBusterLogRepository);

        return appContext;
    }

    private static HikariDataSource initHikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        /*
         * var jdbcUrl = System.getenv("MOVIES_DB_JDBC");
         * var username = System.getenv("MOVIES_DB_USERNAME");
         * var password = System.getenv("MOVIES_DB_PASSWORD");
         */
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/blockbuster-app");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("JUanluis1616*");
        /*
         * hikariConfig.setJdbcUrl(jdbcUrl);
         * hikariConfig.setUsername(username);
         * hikariConfig.setPassword(password);
         */
        hikariConfig.addDataSourceProperty("connectionTestQuery", "SELECT 1");
        hikariConfig.addDataSourceProperty("maximumPoolSize", "4");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return new HikariDataSource(hikariConfig);
    }

    private static CategoryRepository initCategoryRepository(HikariDataSource hikariDataSource) {
        return new CategoryRepositoryImpl(hikariDataSource);
    }

    private static MovieRepository initMovieRepository(HikariDataSource hikariDataSource) {
        return new MovieRepositoryImpl(hikariDataSource);
    }

    private static ClientRepository initClientRepository(HikariDataSource hikariDataSource) {
        return new ClientRepositoryImpl(hikariDataSource);
    }

    private static ReviewRepository initReviewRepository(HikariDataSource hikariDataSource) {
        return new ReviewRepositoryImpl(hikariDataSource);
    }

    private static RentalsRepository initRentalsRepository(HikariDataSource hikariDataSource) {
        return new RentalsRepositoryImpl(hikariDataSource);
    }

    private static BlockbusterLogRepository initBlockbusterLogRepository(HikariDataSource hikariDataSource) {
        return new BlockbusterLogRepositoryImpl(hikariDataSource);
    }

    private static MovieService initMovieService(MovieRepository movieRepository,
            CategoryRepository categoryRepository) {
        return new MovieServiceImpl(movieRepository, categoryRepository);
    }

    private static CategoryService initCategoryService(CategoryRepository categoryRepository) {
        return new CategoryServiceImpl(categoryRepository);
    }

    private static ClientService initClientService(ClientRepository clientRepository) {
        return new ClientServiceImpl(clientRepository);
    }

    private static RentalsService initRentalsService(RentalsRepository rentalsRepository,
            ClientRepository clientRepository, MovieRepository movieRepository) {
        return new RentalsServiceImpl(rentalsRepository, clientRepository, movieRepository);
    }

    private static ReviewService initReviewService(ReviewRepository reviewRepository,
            ClientRepository clientRepository, MovieRepository movieRepository) {
        return new ReviewServiceImpl(reviewRepository, clientRepository, movieRepository);
    }

    private static BlockbusterLogService initBlockbusterLogService(BlockbusterLogRepository blockbusterLogRepository) {
        return new BlockbusterLogServiceImpl(blockbusterLogRepository);
    }

}
