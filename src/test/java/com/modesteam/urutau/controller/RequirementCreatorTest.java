package com.modesteam.urutau.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.builder.ArtifactBuilder;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;

public class RequirementCreatorTest {

	private static final long FAKE_PROJECT_ID = 777L;
	private static final long FAKE_REQUIREMENT_ID = 333L;

	private final Logger logger = Logger.getLogger(RequirementCreator.class);
	
	private MockResult result;
	private UserSession userSession;
	private MockValidator validator;
	private RequirementService requirementService;
	private ProjectService projectService;
	private Project ownedProject;

	@Before
	public void setup() {
		// Catch all..
		logger.setLevel(Level.DEBUG);
		
		// Mocks supported by vraptor
		result = new MockResult();
		validator = new MockValidator();

		// System components
		requirementService = mock(RequirementService.class);
		userSession = mock(UserSession.class);
		projectService = mock(ProjectService.class);
		
		User userMock = mock(User.class);
		
		when(userSession.getUserLogged()).thenReturn(userMock);
		
		ownedProject = createMockProject();
	}

	@Test
	public void createValidFeature() {
		ArtifactBuilder builderFeature = new ArtifactBuilder();
		
		Feature feature = builderFeature
				.id(FAKE_REQUIREMENT_ID)
				.title("Example")
				.description("test unit")
				.projectID(FAKE_PROJECT_ID)
				.buildFeature();
		
		mockWhenProjectLoad(ownedProject);
		
		doNothingWhenCreate(feature);
		
		RequirementCreator controllerMock = 
				new RequirementCreator(result, validator, userSession, projectService, requirementService);
		
		controllerMock.createFeature(feature);
	}
	
	@Test
	public void createValidGeneric() {
		ArtifactBuilder builder = new ArtifactBuilder();
		
		Generic generic = builder
				.id(FAKE_REQUIREMENT_ID)
				.title("Example")
				.description("test unit")
				.projectID(FAKE_PROJECT_ID)
				.buildGeneric();
		
		mockWhenProjectLoad(ownedProject);
		
		doNothingWhenCreate(generic);

		RequirementCreator controllerMock = 
				new RequirementCreator(result, validator, userSession, projectService, requirementService);
		
		controllerMock.createGeneric(generic);
	}

	@Test
	public void createValidEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();
		
		Epic epic = builderEpic
					.id(FAKE_REQUIREMENT_ID)
					.title("Example")
					.description("test unit")
					.projectID(FAKE_PROJECT_ID)
					.buildEpic();

		mockWhenProjectLoad(ownedProject);

		doNothingWhenCreate(epic);
		
		RequirementCreator controllerMock = 
				new RequirementCreator(result, validator, userSession, projectService, requirementService);
		
		controllerMock.createEpic(epic);
	}

	@Test
	public void createValidStorie() {
		ArtifactBuilder builderStorie = new ArtifactBuilder();
				
		Storie storie = builderStorie
				.id(FAKE_REQUIREMENT_ID)
				.title("Example")
				.description("test unit")
				.projectID(FAKE_PROJECT_ID)
				.buildStorie();
		
		mockWhenProjectLoad(ownedProject);
		
		doNothingWhenCreate(storie);
		
		RequirementCreator controllerMock = 
				new RequirementCreator(result, validator, userSession, projectService, requirementService);
		
		controllerMock.createUserStory(storie);
	}

	@Test
	public void createValidUseCase() {
		ArtifactBuilder builderUseCase = new ArtifactBuilder();
		
		UseCase useCase = builderUseCase
				.id(FAKE_REQUIREMENT_ID)
				.title("Example")
				.description("test unit")
				.projectID(FAKE_PROJECT_ID)
				.buildUseCase();
		
		useCase.setFakeActors("Customer");
		
		mockWhenProjectLoad(ownedProject);
		doNothingWhenCreate(useCase);
		
		RequirementCreator controllerMock = 
				new RequirementCreator(result, validator, userSession, projectService, requirementService);
		
		controllerMock.createUseCase(useCase);
	}
	
	/**
	 * Verifies if a requirement with invalid actors can be saved
	 */
	@Test(expected=ValidationException.class)
	public void createInvalidUseCasePassingActor() {
		ArtifactBuilder builderUseCase = new ArtifactBuilder();

		UseCase useCase = builderUseCase
				.id(FAKE_REQUIREMENT_ID)
				.title("Example")
				.description("test unit")
				.projectID(FAKE_PROJECT_ID)
				.buildUseCase();
		
		// Force error
		useCase.setFakeActors(null);
		
		mockWhenProjectLoad(ownedProject);
		doNothingWhenCreate(useCase);
		
		RequirementCreator controllerMock = 
				new RequirementCreator(result, validator, userSession, projectService, requirementService);
		
		controllerMock.createUseCase(useCase);
	}
	
	/**
	 * Verifies if a requirement with an invalid user can be created.
	 */
	@Test(expected=ValidationException.class)
	public void testWithInvalidUser() {
		ArtifactBuilder builder = new ArtifactBuilder();
		
		Generic generic = builder
				.id(FAKE_REQUIREMENT_ID)
				.title("Example")
				.description("test unit")
				.projectID(FAKE_PROJECT_ID)
				.buildGeneric();
		
		UserSession invalidSessionMock = createInvaliUserSession();
				
		RequirementCreator controllerMock = 
				new RequirementCreator(result, validator, invalidSessionMock, projectService, requirementService);
		
		controllerMock.createGeneric(generic);
	}
	
	/**
	 * Verifies if a requirement without an obligatory attribute can be created.
	 */
	@Test(expected=ValidationException.class)
	public void testWithoutTitle() {
		ArtifactBuilder builder = new ArtifactBuilder();
		

		Generic generic = builder
				.id(FAKE_REQUIREMENT_ID)
				.title(null)
				.description("test unit")
				.projectID(FAKE_PROJECT_ID)
				.buildGeneric();

		mockWhenProjectLoad(ownedProject);
		
		RequirementCreator controllerMock = 
				new RequirementCreator(result, validator, userSession, projectService, requirementService);
		
		controllerMock.createGeneric(generic);
	}

	private Project createMockProject() {
		Project ownedProject = mock(Project.class);
		
		when(ownedProject.getProjectID()).thenReturn(FAKE_PROJECT_ID);
		when(ownedProject.getTitle()).thenReturn("Simple test");

		return ownedProject;
	}

	private UserSession createInvaliUserSession() {
		UserSession invalidUserMock = mock(UserSession.class);
		when(invalidUserMock.getUserLogged()).thenReturn(null);
		
		return invalidUserMock;
	}
	
	/**
	 * Mocks DAO create method
	 * 
	 * @param artifact
	 */
	private void doNothingWhenCreate(Artifact artifact) {
		when(requirementService.create(artifact)).thenReturn(true);
	}
	
	private void mockWhenProjectLoad(Project project) {
		when(projectService.load(project.getProjectID())).thenReturn(project);
	}
}