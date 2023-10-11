package com.example.kanban.data;

import com.example.kanban.entity.BoardEntity;
import com.example.kanban.entity.CardEntity;
import com.example.kanban.entity.FeatureEntity;
import com.example.kanban.entity.UserEntity;
import com.example.kanban.enums.FeatureCategory;
import com.example.kanban.enums.Status;
import com.example.kanban.repository.BoardRepository;
import com.example.kanban.repository.CardRepository;
import com.example.kanban.repository.FeatureRepository;
import com.example.kanban.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataGenerator {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final FeatureRepository featureRepository;
    private final CardRepository cardRepository;

    @Autowired
    public DataGenerator(UserRepository userRepository, BoardRepository boardRepository, FeatureRepository featureRepository, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.featureRepository = featureRepository;
        this.cardRepository = cardRepository;
    }

    @PostConstruct
    public void generate() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("John Doe");

        BoardEntity boardEntity = new BoardEntity("Default board", userEntity);

        FeatureEntity featureAdminPage = new FeatureEntity();
        featureAdminPage.setTitle("Administration");
        featureAdminPage.setDescription("This is a feature to give a user ability to administrate the system");
        featureAdminPage.setBoardEntity(boardEntity);
        featureAdminPage.setFeatureCategory(FeatureCategory.FEATURE);
        featureAdminPage.setStatus(Status.BACKLOG);
        boardEntity.addFeature(featureAdminPage);

        CardEntity cardAdminLoginPage = new CardEntity();
        cardAdminLoginPage.setTitle("Create login page for admin user");
        cardAdminLoginPage.setDescription("Only admin users should be able to pass through authorization step");
        cardAdminLoginPage.setStatus(Status.BACKLOG);
        cardAdminLoginPage.setFeature(featureAdminPage);
        featureAdminPage.addCard(cardAdminLoginPage);

        CardEntity cardLogoutButton = new CardEntity();
        cardLogoutButton.setTitle("Create logout mechanism");
        cardLogoutButton.setDescription("Create a button for logging out from admin env");
        cardLogoutButton.setStatus(Status.TO_DO);
        cardLogoutButton.setFeature(featureAdminPage);
        featureAdminPage.addCard(cardLogoutButton);

        CardEntity cardAdministrationTool = new CardEntity();
        cardAdministrationTool.setTitle("Create administration tool");
        cardAdministrationTool.setDescription("The tool should...");
        cardAdministrationTool.setStatus(Status.BACKLOG);
        cardAdministrationTool.setFeature(featureAdminPage);
        featureAdminPage.addCard(cardAdministrationTool);

        FeatureEntity featureSearchMechanism = new FeatureEntity();
        featureSearchMechanism.setTitle("Search mechanism");
        featureSearchMechanism.setDescription("This is a feature to search items in the systems");
        featureSearchMechanism.setBoardEntity(boardEntity);
        featureSearchMechanism.setFeatureCategory(FeatureCategory.FEATURE);
        featureSearchMechanism.setStatus(Status.IN_REVIEW);
        boardEntity.addFeature(featureSearchMechanism);

        CardEntity cardSearchInput = new CardEntity();
        cardSearchInput.setTitle("Create input box on website to enter search phrase");
        cardSearchInput.setDescription("The input box should validate user input in order to avoid malicious data");
        cardSearchInput.setStatus(Status.DONE);
        cardSearchInput.setFeature(featureSearchMechanism);
        featureSearchMechanism.addCard(cardSearchInput);

        CardEntity cardSearchBackendLogic = new CardEntity();
        cardSearchBackendLogic.setTitle("Create a service to search for data with given search phrase");
        cardSearchBackendLogic.setDescription("The service should search across all operational tables");
        cardSearchBackendLogic.setStatus(Status.IN_REVIEW);
        cardSearchBackendLogic.setFeature(featureSearchMechanism);
        featureSearchMechanism.addCard(cardSearchBackendLogic);

        FeatureEntity featureInfrastructure = new FeatureEntity();
        featureInfrastructure.setTitle("Test infrastructure");
        featureInfrastructure.setDescription("Create an environment for testing purposes");
        featureInfrastructure.setBoardEntity(boardEntity);
        featureInfrastructure.setFeatureCategory(FeatureCategory.INFRA);
        featureInfrastructure.setStatus(Status.DOING);
        boardEntity.addFeature(featureInfrastructure);

        CardEntity cardTestInfrastructure = new CardEntity();
        cardTestInfrastructure.setTitle("Create test infrastructure");
        cardTestInfrastructure.setDescription("Create infrastructure for testing...");
        cardTestInfrastructure.setStatus(Status.DOING);
        cardTestInfrastructure.setFeature(featureInfrastructure);
        featureInfrastructure.addCard(cardTestInfrastructure);


        userRepository.save(userEntity);
        boardRepository.save(boardEntity);
        featureRepository.save(featureAdminPage);
        featureRepository.save(featureSearchMechanism);
        featureRepository.save(featureInfrastructure);
        cardRepository.save(cardAdminLoginPage);
        cardRepository.save(cardLogoutButton);
        cardRepository.save(cardAdministrationTool);
        cardRepository.save(cardSearchInput);
        cardRepository.save(cardSearchBackendLogic);
        cardRepository.save(cardTestInfrastructure);
    }
}
