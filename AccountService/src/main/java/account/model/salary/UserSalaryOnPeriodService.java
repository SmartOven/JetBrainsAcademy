package account.model.salary;

import account.exception.DataManagementException;
import account.model.user.UserDetailsEntity;
import account.model.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserSalaryOnPeriodService {

    @Transactional
    public void createAll(List<UserSalaryOnPeriodDto> userSalaryOnPeriodDtoList) {
        userSalaryOnPeriodDtoList.forEach(this::create);
    }

    public void create(UserSalaryOnPeriodDto dto) {
        UserDetailsEntity user = validateUserExistsByEmailAndGet(dto.getEmployee());

        // Map period from String to LocalDate
        LocalDate period = stringToLocalDate(dto.getPeriod());

        // Check that there is no more user-period pairs exist
        validateUserPeriodPairUniqueness(user, period);

        // Create salary
        save(user, period, dto);
    }

    public void update(UserSalaryOnPeriodDto dto) {
        UserDetailsEntity user = validateUserExistsByEmailAndGet(dto.getEmployee());

        // Map period from String to LocalDate
        LocalDate period = stringToLocalDate(dto.getPeriod());

        // Check that this user-period pair exists
        validateUserPeriodPairExists(user, period);

        // Update salary
        save(user, period, dto);
    }

    public UserSalaryOnPeriodInfo findUserSalaryOnPeriodByEmail(String periodString, String email) {
        UserDetailsEntity user = validateUserExistsByEmailAndGet(email);
        LocalDate period = stringToLocalDate(periodString);

        UserSalaryOnPeriod userSalaryOnPeriod = repository.findByUserAndPeriod(user, period)
                .orElseThrow(() -> new NoSuchElementException("There is no salary for this user at this period"));

        return mapper.mappingToInfo(userSalaryOnPeriod);
    }

    void save(UserDetailsEntity user, LocalDate period, UserSalaryOnPeriodDto dto) {
        UserSalaryOnPeriod entity = mapper.mappingToEntity(
                user,
                period,
                dto.getSalary()
        );
        repository.save(entity);
    }

    void validateUserPeriodPairUniqueness(UserDetailsEntity user, LocalDate period) {
        if (repository.existsByUserAndPeriod(user, period)) {
            throw new DataManagementException("Required user already has salary at this period, use PUT method to update it");
        }
    }

    void validateUserPeriodPairExists(UserDetailsEntity user, LocalDate period) {
        if (!repository.existsByUserAndPeriod(user, period)) {
            throw new DataManagementException("Required salary for user on this period doesn't exist, use POST method to create it");
        }
    }

    UserDetailsEntity validateUserExistsByEmailAndGet(String email) {
        return userDetailsService.findByEmail(email)
                .orElseThrow(() -> new DataManagementException("User doesn't exist"));
    }

    static LocalDate stringToLocalDate(String stringDate) {
        Matcher periodMatcher = periodPattern.matcher(stringDate);
        periodMatcher.find();
        int year = Integer.parseInt(periodMatcher.group(2));
        int month = Integer.parseInt(periodMatcher.group(1));
        int day = 1; // doesn't matter witch day to set

        return LocalDate.of(year, month, day);
    }

    private static final Pattern periodPattern = Pattern.compile("([0-9]{2})-([0-9]{4})");

    public UserSalaryOnPeriodService(@Autowired UserSalaryOnPeriodRepository repository,
                                     @Autowired UserSalaryOnPeriodMapper mapper,
                                     @Autowired UserDetailsServiceImpl userDetailsService) {
        this.repository = repository;
        this.mapper = mapper;
        this.userDetailsService = userDetailsService;
    }
    private final UserSalaryOnPeriodRepository repository;
    private final UserSalaryOnPeriodMapper mapper;
    private final UserDetailsServiceImpl userDetailsService;
}
