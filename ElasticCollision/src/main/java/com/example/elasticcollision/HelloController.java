package com.example.elasticcollision;


import javafx.fxml.FXML;
import javafx.animation.AnimationTimer;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;


public class HelloController {

    // ===== FXML NODES =====

    @FXML private Pane playgroundPane;

    @FXML private Circle ball1;
    @FXML private Circle ball2;

    @FXML private Button startStopButton;
    @FXML private Button resetButton;
    @FXML private Button defaultButton;

    @FXML private Spinner<Double> velocity1Spinner;
    @FXML private Spinner<Double> velocity2Spinner;
    @FXML private Spinner<Double> mass1Spinner;
    @FXML private Spinner<Double> mass2Spinner;

    @FXML private ComboBox<MaterialPreset> material1Combo;
    @FXML private ComboBox<MaterialPreset> material2Combo;
    @FXML private ComboBox<MaterialPreset> wallMaterialCombo;
    @FXML private Label frictionLabel;

    @FXML private Label velocity1Label;
    @FXML private Label velocity2Label;
    @FXML private Label energy1Label;
    @FXML private Label energy2Label;
    @FXML private Label restitution1Label;
    @FXML private Label restitution2Label;

    // ===== MODEL =====

    private CollisionObject obj1;
    private CollisionObject obj2;
    private PhysicsProperties physics;

    // ===== ENGINE =====

    private CollisionEngine engine;
    private boolean running;

    // ===== INITIALIZATION =====

    @FXML
    public void initialize() {
        createModel();
        createPhysics();
        setupControls();
        setupButtons();
        startAnimationLoop();
    }

    // ===== SETUP =====

    private void createModel() {
        obj1 = new CollisionObject();
        obj2 = new CollisionObject();

        obj1.setMass(1.0);
        obj1.setRadius(ball1.getRadius());
        obj1.setX(ball1.getCenterX());
        obj1.setStartVelocity(120);
        obj1.setVelocity(0);
        obj1.setRestitutionCoefficient(MaterialPreset.RUBBER.restitution());

        obj2.setMass(1.0);
        obj2.setRadius(ball2.getRadius());
        obj2.setX(ball2.getCenterX());
        obj2.setStartVelocity(-150);
        obj2.setVelocity(0);
        obj2.setRestitutionCoefficient(MaterialPreset.RUBBER.restitution());
    }

    private void createPhysics() {
        physics = new PhysicsProperties();
        physics.setWallRestitution(MaterialPreset.RUBBER.restitution());
        physics.setFrictionCoefficient(FrictionPreset.DRY_WOOD.coefficient());

        engine = new CollisionEngine(0.016); // ~60 FPS
    }

    private void setupControls() {

        velocity1Spinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(-150, 100, 120, 1));
        velocity2Spinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(-150, 100, -150, 1));

        mass1Spinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 4.0, 1.0, 0.1));
        mass2Spinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 4.0, 1.0, 0.1));

        material1Combo.getItems().setAll(MaterialPreset.values());
        material2Combo.getItems().setAll(MaterialPreset.values());
        wallMaterialCombo.getItems().setAll(MaterialPreset.values());


        material1Combo.getSelectionModel().select(MaterialPreset.RUBBER);
        material2Combo.getSelectionModel().select(MaterialPreset.RUBBER);
        wallMaterialCombo.getSelectionModel().select(MaterialPreset.RUBBER);


        bindBall(obj1, velocity1Spinner, mass1Spinner, material1Combo);
        bindBall(obj2, velocity2Spinner, mass2Spinner, material2Combo);

        wallMaterialCombo.valueProperty().addListener((a, b, c) ->
                physics.setWallRestitution(c.restitution()));


    }

    private void setupButtons() {

        startStopButton.setOnAction(e -> toggleSimulation());

        resetButton.setOnAction(e -> resetSimulation());

        defaultButton.setOnAction(e -> {
            resetSimulation();
            velocity1Spinner.getValueFactory().setValue(120.0);
            velocity2Spinner.getValueFactory().setValue(-150.0);
            mass1Spinner.getValueFactory().setValue(1.0);
            mass2Spinner.getValueFactory().setValue(1.0);
            material1Combo.getSelectionModel().select(MaterialPreset.RUBBER);
            material2Combo.getSelectionModel().select(MaterialPreset.RUBBER);
            wallMaterialCombo.getSelectionModel().select(MaterialPreset.RUBBER);

        });
    }

    // ===== BINDINGS =====

    private void bindBall(
            CollisionObject obj,
            Spinner<Double> velocity,
            Spinner<Double> mass,
            ComboBox<MaterialPreset> material
    ) {
        velocity.valueProperty().addListener((a, b, c) ->
                obj.setStartVelocity(c));

        mass.valueProperty().addListener((a, b, c) ->
                obj.setMass(c));

        material.valueProperty().addListener((a, b, c) ->
                obj.setRestitutionCoefficient(c.restitution()));
    }

    // ===== SIMULATION CONTROL =====

    private void toggleSimulation() {
        running = !running;

        if (running) {
            obj1.setVelocity(obj1.getStartVelocity());
            obj2.setVelocity(obj2.getStartVelocity());
            startStopButton.setText("Stop");
        } else {
            startStopButton.setText("Start");
        }
    }

    private void resetSimulation() {
        running = false;
        startStopButton.setText("Start");

        obj1.setVelocity(0);
        obj2.setVelocity(0);

        obj1.setX(50);
        obj2.setX(playgroundPane.getWidth() - 50);

        ball1.setCenterX(obj1.getX());
        ball2.setCenterX(obj2.getX());
    }

    // ===== ANIMATION LOOP =====

    private void startAnimationLoop() {

        new AnimationTimer() {

            @Override
            public void handle(long now) {

                if (running) {
                    engine.update(
                            obj1,
                            obj2,
                            physics,
                            playgroundPane.getWidth()
                    );
                }

                ball1.setCenterX(obj1.getX());
                ball2.setCenterX(obj2.getX());

                velocity1Label.setText(
                        String.format("%.2f m/s", obj1.getVelocity()));
                velocity2Label.setText(
                        String.format("%.2f m/s", obj2.getVelocity()));

                energy1Label.setText(
                        String.format("%.2f J", obj1.kineticEnergy()));
                energy2Label.setText(
                        String.format("%.2f J", obj2.kineticEnergy()));

                restitution1Label.setText(
                        String.format("%.2f", obj1.getRestitutionCoefficient()));
                restitution2Label.setText(
                        String.format("%.2f", obj2.getRestitutionCoefficient()));
            }

        }.start();
    }

    public Label getFrictionLabel() {
        return frictionLabel;
    }

    public void setFrictionLabel(Label frictionLabel) {
        this.frictionLabel = frictionLabel;
    }
}