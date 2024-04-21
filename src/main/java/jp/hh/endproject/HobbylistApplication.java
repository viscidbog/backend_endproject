package jp.hh.endproject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jp.hh.endproject.domain.Hobby;
import jp.hh.endproject.domain.HobbyRepository;

import jp.hh.endproject.domain.Genre;
import jp.hh.endproject.domain.GenreRepository;

import jp.hh.endproject.domain.AppUser;
import jp.hh.endproject.domain.AppUserRepository;

import jp.hh.endproject.domain.HobbyType;
import jp.hh.endproject.domain.TypeRepository;

@SpringBootApplication
public class HobbylistApplication implements CommandLineRunner {

	private final HobbyRepository hobbyRepository;
	private final GenreRepository genreRepository;
	private final AppUserRepository appUserRepository;
	private final TypeRepository typeRepository;

	public HobbylistApplication(HobbyRepository hobbyRepository, GenreRepository genreRepository,
			AppUserRepository appUserRepository, TypeRepository typeRepository) {
		this.hobbyRepository = hobbyRepository;
		this.genreRepository = genreRepository;
		this.appUserRepository = appUserRepository;
		this.typeRepository = typeRepository;
	}

	private static final Logger log = LoggerFactory.getLogger(HobbylistApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HobbylistApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		log.info("default genres to repo");
		Genre genre1 = new Genre("Sci-Fi");
		Genre genre2 = new Genre("Action");
		Genre genre3 = new Genre("Adventure");
		Genre genre4 = new Genre("Strategy");
		Genre genre5 = new Genre("Fantasy");
		Genre genre6 = new Genre("Western");
		Genre genre7 = new Genre("Military");
		Genre genre8 = new Genre("History");
		genreRepository.save(genre1);
		genreRepository.save(genre2);
		genreRepository.save(genre3);
		genreRepository.save(genre4);
		genreRepository.save(genre5);
		genreRepository.save(genre6);
		genreRepository.save(genre7);
		genreRepository.save(genre8);
		// this logs all genres to log to make sure they are entered correctly
		for (Genre genre : genreRepository.findAll()) {
			log.info(genre.getName());
		}

		log.info("default types to repo");
		HobbyType hobbyType1 = new HobbyType("Book");
		HobbyType hobbyType2 = new HobbyType("PC Game");
		HobbyType hobbyType3 = new HobbyType("Console Game");
		HobbyType hobbyType4 = new HobbyType("Movie");
		typeRepository.save(hobbyType1);
		typeRepository.save(hobbyType2);
		typeRepository.save(hobbyType3);
		typeRepository.save(hobbyType4);
		// this logs all hobby types to log to make sure they are entered correctly
		for (HobbyType hobbyType : typeRepository.findAll()) {
			log.info(hobbyType.getName());
		}

		// tässä on tehty genren setti arrays.aslistin kans. ei välttis tarvi mihinkään
		// toimintoon, mut
		// muistaa miten se tehdään sit
		Set<Genre> genres = new HashSet<Genre>(Arrays.asList(genre1, genre2));

		Hobby hobby1 = new Hobby("Stellaris", "Paradox Interactive", "2016",
				"A space 'Grand Strategy' game, meaning everything is large-scale and abstracted. Ostensibly real-time but in essence plays a lot like a turn-based one. Very complex, involved and difficult, but rewarding.",
				"Yes", hobbyType2,
				new HashSet<Genre>(Arrays.asList(genre1, genre4)));

		hobbyRepository.save(hobby1);

		Hobby hobby2 = new Hobby("Gamegame", "Authorauthor", "2020",
				"Descriptiondescriptiondescriptiondescriptiondescription", "Yes", hobbyType3,
				new HashSet<Genre>(Arrays.asList(genre2, genre5)));

		hobbyRepository.save(hobby2);

		Hobby hobby3 = new Hobby("Lord of the Rings", "J.R.R. Tolkien", "1955",
				"It's LoTR. The grandaddy of all fantasy and giant doorstopper. Everything is so excruciatingly detailed that it's a slog to read through. Not my thing, but it seems to be for a lot of others.",
				"No", hobbyType1, new HashSet<Genre>(Arrays.asList(genre3, genre5)));

		hobbyRepository.save(hobby3);

		Hobby hobby4 = new Hobby("Spectral", "Nic Mathieu", "2016",
				"The plot can be summed up with: Soldiers versus ghosts. It sounds stupid, but if you get into the movie with no expectations, you will most likely enjoy it. The production quality is very good.",
				"Yes", hobbyType4, new HashSet<Genre>(Arrays.asList(genre1, genre7)));

		hobbyRepository.save(hobby4);

		Hobby hobby5 = new Hobby("The Settlers II", "Blue Byte Software", "1996",
				"A classic management and strategy game. In my opinion the best in the series. For its time, great graphics, sound and especially gameplay. Incredibly enjoyable especially for long games.",
				"Yes", hobbyType2,
				new HashSet<Genre>(Arrays.asList(genre4, genre8)));

		hobbyRepository.save(hobby5);

		AppUser user1 = new AppUser("admin",
				"$2a$10$47CDOOBtMSGTH3Yuwkng4.7c9cgNxcsf9WJx7PiAEdUsRB5zBW3Jq",
				"ADMIN");

		AppUser user2 = new AppUser("user",
				"$2a$10$c76N.PIMrGwYYo.3VZmHyO4dEdvpKdvHLF0CJIkgQ/Xmde5ODTi8e",
				"USER");

		appUserRepository.save(user1);
		appUserRepository.save(user2);

		log.info("fetch all hobbies:");
		for (Hobby hobby : hobbyRepository.findAll()) {
			log.info(hobby.toString());
		}

		log.info("fetch all genres:");
		for (Genre genre : genreRepository.findAll()) {
			log.info(genre.toString());
		}

		log.info("fetch all types:");
		for (HobbyType hobbyType : typeRepository.findAll()) {
			log.info(hobbyType.toString());
		}

	};
}
