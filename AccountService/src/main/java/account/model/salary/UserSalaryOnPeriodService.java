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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserSalaryOnPeriodService {

    @Transactional
    public void createAll(List<UserSalaryOnPeriodDto> userSalaryOnPeriodDtoList) {
        userSalaryOnPeriodDtoList.forEach(this::create);
    }

    public void create(UserSalaryOnPeriodDto dto) {
        UserDetailsEntity user = validateUserExistsByEmailAndGet(dto.getEmployee());

        // Map period from String to LocalDate
        LocalDate period = validateStringAndConvertToLocalDate(dto.getPeriod());

        // Check that there is no more user-period pairs exist
        validateUserPeriodPairUniqueness(user, period);

        // Check that salary is not negative
        Long salary = dto.getSalary();
        validateSalary(salary);

        // Create salary
        UserSalaryOnPeriod entity = mapper.mappingToEntity(
                user,
                period,
                salary
        );
        repository.save(entity);
    }

    public void update(UserSalaryOnPeriodDto dto) {
        UserDetailsEntity user = validateUserExistsByEmailAndGet(dto.getEmployee());

        // Map period from String to LocalDate
        LocalDate period = validateStringAndConvertToLocalDate(dto.getPeriod());

        // Check that this user-period pair exists and get it
        UserSalaryOnPeriod entity = validateUserPeriodPairExistsAndGet(user, period);

        // Update fields
        entity = mapper.mappingToEntity(entity, user, period, dto.getSalary());

        // Update salary
        repository.save(entity);
    }

    public UserSalaryOnPeriodInfo findUserSalaryOnPeriodByEmailAndPeriod(String periodString, String email) {
        UserDetailsEntity user = validateUserExistsByEmailAndGet(email);
        LocalDate period = validateStringAndConvertToLocalDate(periodString);

        UserSalaryOnPeriod userSalaryOnPeriod = repository.findByUserAndPeriod(user, period)
                .orElseThrow(() -> new NoSuchElementException("There is no salary for this user at this period"));

        return mapper.mappingToInfo(userSalaryOnPeriod);
    }

    public List<UserSalaryOnPeriodInfo> findUserSalaryOnPeriodByEmail(String email) {
        UserDetailsEntity user = validateUserExistsByEmailAndGet(email);
        return repository.findAllByUser(user)
                .stream()
                .sorted((o1, o2) -> o2.getPeriod().compareTo(o1.getPeriod()))
                .map(mapper::mappingToInfo)
                .collect(Collectors.toList());
    }

    void validateUserPeriodPairUniqueness(UserDetailsEntity user, LocalDate period) {
        if (repository.existsByUserAndPeriod(user, period)) {
            throw new DataManagementException("Required user already has salary at this period, use PUT method to update it");
        }
    }

    UserSalaryOnPeriod validateUserPeriodPairExistsAndGet(UserDetailsEntity user, LocalDate period) {
        return repository.findByUserAndPeriod(user, period)
                .orElseThrow(() -> new DataManagementException("Required salary for user on this period doesn't exist, use POST method to create it"));
    }

    UserDetailsEntity validateUserExistsByEmailAndGet(String email) {
        return userDetailsService.findByEmail(email)
                .orElseThrow(() -> new DataManagementException("User doesn't exist"));
    }

    void validateSalary(Long salary) {
        if (salary == null) {
            throw new DataManagementException("Salary can't be null");
        }
        if (salary < 0) {
            throw new DataManagementException("Salary can't be negative");
        }
    }

    static LocalDate validateStringAndConvertToLocalDate(String stringDate) {
        Matcher periodMatcher = periodPattern.matcher(stringDate);
        boolean findingResult = periodMatcher.find();
        int year = Integer.parseInt(periodMatcher.group(2));
        int month = Integer.parseInt(periodMatcher.group(1));
        int day = 1; // doesn't matter witch day to set
        if (year < 1) {
            throw new DataManagementException("Year can't be negative");
        }
        if (month < 1 || month > 12) {
            throw new DataManagementException("Month should be in range from 1 to 12");
        }

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
