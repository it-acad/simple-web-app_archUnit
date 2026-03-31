package chnu.edu.pz.simplewebapp;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;


@AnalyzeClasses(packages = "chnu.edu.pz.simplewebapp")
public class ArchitectureTests {
    // 1.Layered Architecture------------------------------------------------------------------------

    @ArchTest
    static final ArchRule layer_dependencies_are_respected = layeredArchitecture()
            .consideringAllDependencies()
            .layer("Controllers").definedBy("..controller..")
            .layer("Services").definedBy("..service..")
            .layer("Repositories").definedBy("..repository..")
            .layer("Models").definedBy("..model..")

            .whereLayer("Controllers").mayNotBeAccessedByAnyLayer()
            .whereLayer("Services").mayOnlyBeAccessedByLayers("Controllers")
            .whereLayer("Repositories").mayOnlyBeAccessedByLayers("Services");

    @ArchTest
    static final ArchRule controllers_should_not_access_repositories_directly = noClasses()
            .that().resideInAPackage("..controller..")
            .should().dependOnClassesThat().resideInAPackage("..repository..");

    @ArchTest
    static final ArchRule models_should_not_depend_on_other_layers = noClasses()
            .that().resideInAPackage("..model..")
            .should().dependOnClassesThat().resideInAnyPackage("..controller..", "..service..", "..repository..");

    // Naming Conventions-----------------------------------------------------------------------------

    @ArchTest
    static final ArchRule controllers_should_be_suffixed = classes()
            .that().resideInAPackage("..controller..")
            .should().haveSimpleNameEndingWith("Controller");

    @ArchTest
    static final ArchRule services_should_be_suffixed = classes()
            .that().resideInAPackage("..service..")
            .should().haveSimpleNameEndingWith("Service");

    @ArchTest
    static final ArchRule repositories_should_be_suffixed = classes()
            .that().resideInAPackage("..repository..")
            .should().haveSimpleNameEndingWith("Repository");

    @ArchTest
    static final ArchRule classes_named_controller_should_be_in_controller_package = classes()
            .that().haveSimpleNameEndingWith("Controller")
            .should().resideInAPackage("..controller..");

    @ArchTest
    static final ArchRule classes_named_service_should_be_in_service_package = classes()
            .that().haveSimpleNameEndingWith("Service")
            .should().resideInAPackage("..service..");

    @ArchTest
    static final ArchRule classes_named_repository_should_be_in_repository_package = classes()
            .that().haveSimpleNameEndingWith("Repository")
            .should().resideInAPackage("..repository..");

    //Annotations-------------------------------------------------------------------------------------

    @ArchTest
    static final ArchRule controllers_should_be_annotated = classes()
            .that().resideInAPackage("..controller..")
            .should().beAnnotatedWith(RestController.class)
            .orShould().beAnnotatedWith(Controller.class);

    @ArchTest
    static final ArchRule services_should_be_annotated = classes()
            .that().resideInAPackage("..service..")
            .should().beAnnotatedWith(Service.class);

    @ArchTest
    static final ArchRule repositories_should_be_annotated = classes()
            .that().resideInAPackage("..repository..")
            .and().areInterfaces()
            .should().beAnnotatedWith(Repository.class);

    @ArchTest
    static final ArchRule classes_annotated_with_rest_controller_should_be_in_controller_package = classes()
            .that().areAnnotatedWith(RestController.class)
            .should().resideInAPackage("..controller..");

    @ArchTest
    static final ArchRule classes_annotated_with_service_should_be_in_service_package = classes()
            .that().areAnnotatedWith(Service.class)
            .should().resideInAPackage("..service..");

    //Clean Code & Best Practices

    @ArchTest
    static final ArchRule no_field_injection = fields()
            .should().notBeAnnotatedWith(Autowired.class)
            .because("Field injection is considered a bad practice. Use constructor injection instead.");

    @ArchTest
    static final ArchRule interfaces_should_not_have_names_ending_with_the_word_interface = noClasses()
            .that().areInterfaces()
            .should().haveSimpleNameEndingWith("Interface");

    @ArchTest
    static final ArchRule model_classes_should_not_be_annotated_with_spring_stereotypes = classes()
            .that().resideInAPackage("..model..")
            .should().notBeAnnotatedWith(Service.class)
            .andShould().notBeAnnotatedWith(Repository.class)
            .andShould().notBeAnnotatedWith(Controller.class)
            .andShould().notBeAnnotatedWith(RestController.class);

    @ArchTest
    static final ArchRule utility_classes_should_not_be_in_core_layers = noClasses()
            .that().haveSimpleNameEndingWith("Utils")
            .or().haveSimpleNameEndingWith("Util")
            .should().resideInAnyPackage("..controller..", "..repository..")
            .allowEmptyShould(true);

    @ArchTest
    static final ArchRule no_classes_should_use_java_util_logging = noClasses()
            .should().dependOnClassesThat().resideInAPackage("java.util.logging")
            .because("SLF4J or Logback should be used instead of standard java.util.logging");

    @ArchTest
    static final ArchRule services_should_not_depend_on_controllers = noClasses()
            .that().resideInAPackage("..service..")
            .should().dependOnClassesThat().resideInAPackage("..controller..");
}