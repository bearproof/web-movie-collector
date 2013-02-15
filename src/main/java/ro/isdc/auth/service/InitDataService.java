package ro.isdc.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.isdc.auth.domain.Account;
import ro.isdc.auth.domain.Movie;
import ro.isdc.auth.domain.Role;
import ro.isdc.auth.repository.AccountRepository;
import ro.isdc.auth.repository.MovieRepository;
import ro.isdc.auth.repository.RoleRepository;
import ro.isdc.utils.Generator;

/**
 * Data initialization service
 * 
 * @author Adrian.Ursu
 * 
 */
@Service("initDataService")
public class InitDataService {

	private static final Logger log = Logger.getLogger(InitDataService.class);

	public final String[] genreList = { "Action", "Comedy", "Family", "History", "Sci-Fi", "Adventure", "Animation", "Drama", "Thriller", "Film-Noir", "Horror" };

	@Autowired
	AccountRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	MovieRepository movieRepository;

	public void init() {
		log.debug("Generating random accounts for test data");
		/* A user with admin right */
		Role adminRole = getRole("ROLE_ADMIN");
		Role userRole = getRole("ROLE_USER");

		Account admin = new Account();
		admin.setFirstName("John");
		admin.setLastName("Doe");
		admin.setEmail("admin@mail.com");
		admin.setPassword(DigestUtils.md5Hex("admin"));

		admin.setIsEnabled(true);
		this.userRepository.save(admin);
		admin.getRoles().add(adminRole);
		admin.getRoles().add(userRole);
		this.userRepository.save(admin);
		/* A user with no admin right */
		Account user = new Account();
		user.setFirstName("Jane");
		user.setLastName("Doe");
		user.setEmail("user@mail.com");
		user.setPassword(DigestUtils.md5Hex("user"));
		user.setIsEnabled(true);
		this.userRepository.save(user);
		user.getRoles().add(userRole);
		this.userRepository.save(user);

		// add 100 more users without roles (To test pagination)
		List<Account> listUsers = new ArrayList<Account>();
		Account aUser;
		List<Movie> movieList = new ArrayList<Movie>();
		Movie aMovie;
		for (int i = 0; i < 100; i++) {
			aUser = new Account();
			aUser.setFirstName("FirstName_" + i);
			aUser.setLastName("LastName_" + i);
			aUser.setEmail("User_" + i + "@mail.com");
			aUser.setPassword(DigestUtils.md5Hex("user"));
			aUser.setIsEnabled(true);
			listUsers.add(aUser);

		}
		this.userRepository.save(listUsers);

		List<String> movieNames = Generator.movieNames;

		log.debug("Generating a new random movie object for test data...");
		for (int i = 0; i < movieNames.size() - 1; i++) {
			aMovie = new Movie();
			aMovie.setTitle(movieNames.get(i));

			int numberOfActor = randomNumGenerator(1, 15);
			StringBuilder castBuilder = new StringBuilder();
			for (int j = 0; j < numberOfActor; j++) {
				if (j != numberOfActor - 1)
					castBuilder.append(" " + Generator.getActorName() + ", ");
				else
					castBuilder.append(" " + Generator.getActorName() + "");
			}
			aMovie.setCast(castBuilder.toString());

			aMovie.setDescription(Generator.getPhrase(50));
			aMovie.setDirector(Generator.getDirectorName());
			aMovie.setGenre(genreList[randomNumGenerator(0, genreList.length - 1)]);
			aMovie.setIdOnSite("testIdOnSite"); // the same
			if (i % 5 != 0) {
				if (i % 3 == 0) {
					aMovie.setLentTo(Generator.getFirstName(true));
				} else {
					aMovie.setLentTo(Generator.getFirstName(false));
				}
				aMovie.setLoanDate(String.valueOf(System.currentTimeMillis()));
				aMovie.setReturnDate(String.valueOf(Long.parseLong(aMovie.getLoanDate()) + randomLongNumGenerator(604800000l, 7889000000l))); // the
			} else {
				/*
				 * aMovie.setLentTo(""); aMovie.setLoanDate("");
				 * aMovie.setReturnDate("");
				 */
			}
			aMovie.setMovieStatus(String.valueOf(randomNumGenerator(0, 3)));
			aMovie.setOwnMovieNotes(Generator.getPhrase(30));
			aMovie.setRate(String.valueOf(randomNumGenerator(1, 10)));

			aMovie.setRuntime(String.valueOf(randomNumGenerator(100, 150)));
			aMovie.setShelfLocation(String.valueOf(i)); // the iteration value
			aMovie.setSite("testSite");
			aMovie.setUserId(admin.getId());
			aMovie.setUserRating(String.valueOf(randomNumGenerator(0, 4)));
			aMovie.setYear(String.valueOf(randomNumGenerator(1990, 2012)));

			movieList.add(aMovie);
		}
		this.movieRepository.save(movieList);

	}

	private Role getRole(final String roleId) {
		Role result = roleRepository.findByRoleName(roleId);
		if (result == null) {
			result = new Role();
			result.setRoleName(roleId);
			roleRepository.save(result);
		}
		return result;
	}

	private int randomNumGenerator(int min, int max) {
		return (min + (int) (Math.random() * ((max - min) + 1)));
	}

	// TODO: Delete this later
	private long randomLongNumGenerator(long min, long max) {
		return (min + (long) (Math.random() * ((max - min) + 1)));
	}

}
