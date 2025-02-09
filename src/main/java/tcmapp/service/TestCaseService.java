package tcmapp.service;

import com.atlassian.activeobjects.external.ActiveObjects;
import tcmapp.entity.TestCaseEntity;
import net.java.ao.Query;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class TestCaseService {
    private final ActiveObjects ao;

    @Inject
    public TestCaseService(ActiveObjects ao) {
        this.ao = ao;
    }

    public TestCaseEntity createTestCase(String name, String description) {
        return ao.executeInTransaction(() -> {
            TestCaseEntity testCase = ao.create(TestCaseEntity.class);
            testCase.setName(name);
            testCase.setDescription(description);
            testCase.save();
            return testCase;
        });
    }

    public List<TestCaseEntity> getAllTestCases() {
        return ao.executeInTransaction(() -> Arrays.stream(ao.find(TestCaseEntity.class))
                .collect(Collectors.toList()));
    }
}
