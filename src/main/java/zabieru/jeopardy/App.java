package zabieru.jeopardy;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

public class App extends Application {
    Controller controller = new Controller();
    Scene scene;
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

    public void start(Stage stage) {
        stage.setTitle("Jeopardy Maker");
        int width = size.width;
        int height = (size.height - 80);
        VBox root = new VBox();
        Text title = new Text("Jeopardy Board Maker");
        try {
            Font font = Font.loadFont(new File("res/FredokaOne-Regular.ttf").toURI().toURL().toExternalForm(), 30);
            title.setFont(font);
        } catch (IOException E) {
            System.out.println("font not found");
        }
        Text selected_Board = new Text("Board Selected : ");
        Text_Properties(selected_Board);
        Button play = new Button("Play");
        Button create_board = new Button("Create Board");
        Button load_board = new Button("Load Board");
        button_properties(play);
        button_properties(create_board);
        button_properties(load_board);
        root.getChildren().addAll(title, selected_Board, play, create_board, load_board);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();

        HBox navBar = new HBox();
        Text return_button = new Text("<-- Return to Create Board");
        Text_Properties(return_button);
        return_button.setUnderline(true);
        navBar.getChildren().add(return_button);
        Insets insets = new Insets(20);
        navBar.setPadding(insets);
        navBar.setAlignment(Pos.TOP_LEFT);
//---------------------------------------------------------------------------------------

        VBox create_Board_Vbox = new VBox();
        HBox num_categories = new HBox();
        Text num_categories_text = new Text("Enter Category Name:");
        Text_Properties(num_categories_text);
        TextField number_categories_textfield = new TextField("Category");
        TextField_properties(number_categories_textfield, 300);
        Button number_categories_button = new Button("Add category");
        button_properties(number_categories_button);
        Button remove = new Button("Remove last category");
        button_properties(remove);
        remove.setMinWidth(300);
        remove.setStyle("-fx-background-color: linear-gradient(to bottom,#fa4343,#bf0000)");
        num_categories.getChildren().addAll(num_categories_text, number_categories_textfield, number_categories_button, remove);
        num_categories.setAlignment(Pos.CENTER);
        num_categories.setSpacing(10);


        HBox board = new HBox();
        board.setAlignment(Pos.CENTER);
        board.setMinHeight(300);

        HBox finalise = new HBox();
        TextField board_Name = new TextField("Enter a name...");
        TextField_properties(board_Name, 200);
        Button save_board = new Button("Save Board");
        button_properties(save_board);
        finalise.getChildren().addAll(board_Name, save_board);
        finalise.setSpacing(20);
        finalise.setAlignment(Pos.CENTER);
        create_Board_Vbox.getChildren().addAll(navBar, num_categories, board, finalise);
        create_Board_Vbox.setSpacing(40);
        create_board.setAlignment(Pos.TOP_CENTER);
        Scene create_board_scene = new Scene(create_Board_Vbox, width, height);
//---------------------------------------------------------------------------------------------------------
        HBox navBar2 = new HBox();
        Text return_button2 = new Text("<-- Return to Create Board");
        Text_Properties(return_button2);
        return_button2.setUnderline(true);
        navBar2.getChildren().add(return_button2);
        Insets insets2 = new Insets(20);
        navBar2.setPadding(insets2);
        navBar2.setAlignment(Pos.TOP_LEFT);
        VBox load_board_vbox = new VBox();
        Text load_board_title = new Text("Load Board Menu");
        Text_Properties(load_board_title);
        ListView<Text> listView = new ListView<>();
        listView.setMaxWidth(400);
        Button load_button = new Button("Load Board");
        button_properties(load_button);
        load_board_vbox.getChildren().addAll(navBar2, load_board_title, listView, load_button);
        load_board_vbox.setAlignment(Pos.TOP_CENTER);
        load_board_vbox.setSpacing(30);
        Scene load_board_scene = new Scene(load_board_vbox, width, height);

//---------------------------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------------------------

//---------------------------------------------------------------------------------------------------------

//---------------------------------------------------------------------------------------------------------

        return_button2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                return_button2.setFill(Paint.valueOf("#03c6fc"));
            }
        });
        return_button2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                return_button2.setFill(Paint.valueOf("#000000"));
            }
        });


        return_button2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selected_Board.setText("Board Selected : ");
                stage.setScene(scene);
                stage.show();
            }
        });

        return_button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                return_button.setFill(Paint.valueOf("#03c6fc"));
            }
        });
        return_button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                return_button.setFill(Paint.valueOf("#000000"));
            }
        });


        return_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selected_Board.setText("Board Selected : ");
                stage.setScene(scene);
                stage.show();
            }
        });
        play.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (controller.get_num_categories() == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("No board was selected");
                    alert.show();
                } else {
                    Scene player_init = get_player_init_scene(width, height, stage);
                    stage.setScene(player_init);
                    stage.show();
                }
            }
        });


        load_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Text selected = listView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    controller.load_board(selected.getText());
                }
                play.setOpacity(1);
                selected_Board.setText("Board Selected : " + selected.getText());
                stage.setScene(scene);
                stage.show();
            }
        });


        load_board.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.reset();
                File boards = new File("Boards/");
                ObservableList<Text> temp = FXCollections.observableArrayList();
                for (File file : Objects.requireNonNull(boards.listFiles())) {
                    Text temp_text = new Text(file.getName());
                    Text_Properties(temp_text);
                    temp.add(temp_text);
                }
                listView.setItems(temp);
                stage.setScene(load_board_scene);
                stage.show();
            }
        });
        save_board.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String name = board_Name.getText();
                if (controller.check_board()) {
                    controller.save_board(name);
                    selected_Board.setText("Board Selected : " + name);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Some of the questions were not initialized or the board has a size of 0");
                    alert.show();
                }
            }
        });
        create_board.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.reset();
                board.getChildren().clear();
                Question[][] temp_board = controller.get_board();
                String[] categories = controller.get_categories();
                for (int i = 0; i < temp_board.length; ++i) {
                    VBox temp_Vbox = new VBox();
                    VBox text_box = new VBox();
                    Text text = new Text(categories[i]);
                    text.setTextAlignment(TextAlignment.CENTER);
                    Text_Properties(text);
                    text.setWrappingWidth(200);
                    text_box.getChildren().add(text);
                    text_box.setMinHeight(100);
                    text_box.setMaxHeight(100);
                    text_box.setAlignment(Pos.CENTER);
                    temp_Vbox.getChildren().add(text_box);
                    for (int j = 0; j < 5; ++j) {
                        int value = (j + 1) * 100;
                        Button temp_button = new Button(Integer.toString(value));
                        if (temp_board[i][j] == null) {
                            question_not_set(temp_button);
                            create_question(temp_button, stage, controller, null, i * 5 + j, board, create_board_scene, categories[i]);
                        } else {
                            button_properties(temp_button);
                            create_question(temp_button, stage, controller, temp_board[i][j], i * 5 + j, board, create_board_scene, categories[i]);
                        }
                        temp_Vbox.getChildren().add(temp_button);
                    }
                    board.getChildren().add(temp_Vbox);
                }
                stage.setScene(create_board_scene);
                stage.show();
            }
        });
        remove.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int num_categories = controller.get_num_categories();
                controller.change_num_category(num_categories - 1);
                Question[][] temp_board = controller.get_board();
                String[] categories = controller.get_categories();
                board.getChildren().clear();
                for (int i = 0; i < temp_board.length; ++i) {
                    VBox temp_Vbox = new VBox();
                    VBox text_box = new VBox();
                    Text text = new Text(categories[i]);
                    text.setTextAlignment(TextAlignment.CENTER);
                    Text_Properties(text);
                    text.setWrappingWidth(200);
                    text_box.getChildren().add(text);
                    text_box.setMinHeight(100);
                    text_box.setMaxHeight(100);
                    text_box.setAlignment(Pos.CENTER);
                    temp_Vbox.getChildren().add(text_box);
                    for (int j = 0; j < 5; ++j) {
                        int value = (j + 1) * 100;
                        Button temp_button = new Button(Integer.toString(value));
                        if (temp_board[i][j] == null) {
                            question_not_set(temp_button);
                            create_question(temp_button, stage, controller, null, i * 5 + j, board, create_board_scene, categories[i]);
                        } else {
                            button_properties(temp_button);
                            create_question(temp_button, stage, controller, temp_board[i][j], i * 5 + j, board, create_board_scene, categories[i]);
                        }
                        temp_Vbox.getChildren().add(temp_button);
                    }
                    board.getChildren().add(temp_Vbox);
                }
            }
        });
        number_categories_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int num_categories = controller.get_num_categories();
                controller.change_num_category(num_categories + 1);
                int maximum = Math.min(number_categories_textfield.getText().length(), 50);
                controller.add_categorie(number_categories_textfield.getText(0, maximum), num_categories);
                board.getChildren().clear();
                Question[][] temp_board = controller.get_board();
                String[] categories = controller.get_categories();
                for (int i = 0; i < temp_board.length; ++i) {
                    VBox temp_Vbox = new VBox();
                    VBox text_box = new VBox();
                    Text text = new Text(categories[i]);
                    text.setTextAlignment(TextAlignment.CENTER);
                    Text_Properties(text);
                    text.setWrappingWidth(200);
                    text_box.getChildren().add(text);
                    text_box.setMinHeight(100);
                    text_box.setMaxHeight(100);
                    text_box.setAlignment(Pos.CENTER);
                    temp_Vbox.getChildren().add(text_box);
                    for (int j = 0; j < 5; ++j) {
                        int value = (j + 1) * 100;
                        Button temp_button = new Button(Integer.toString(value));
                        if (temp_board[i][j] == null) {
                            question_not_set(temp_button);
                            create_question(temp_button, stage, controller, null, i * 5 + j, board, create_board_scene, categories[i]);
                        } else {
                            button_properties(temp_button);
                            create_question(temp_button, stage, controller, temp_board[i][j], i * 5 + j, board, create_board_scene, categories[i]);
                        }
                        temp_Vbox.getChildren().add(temp_button);
                    }
                    board.getChildren().add(temp_Vbox);
                }
                number_categories_textfield.setText("Category");
            }
        });
    }

    public void button_properties(Button button) {
        button.setMinWidth(100);
        button.setMaxWidth(200);
        button.setPrefWidth(150);
        button.setStyle("-fx-background-color: linear-gradient(to bottom,#7ae3fa,#02a4c7)");
        try {
            Font font = Font.loadFont(new File("res/FredokaOne-Regular.ttf").toURI().toURL().toExternalForm(), 18);
            button.setFont(font);
        } catch (IOException E) {
            System.out.println("font not found");
        }
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setOpacity(.5);
            }
        });
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setOpacity(1);
            }
        });
    }

    public void TextField_properties(TextField textField, int size) {
        String temp_text = textField.getText();
        textField.setMaxWidth(size * 2);
        textField.setPrefWidth(size);
        try {
            Font font = Font.loadFont(new File("res/FredokaOne-Regular.ttf").toURI().toURL().toExternalForm(), 18);
            textField.setFont(font);
        } catch (IOException E) {
            System.out.println("font not found");
        }
        textField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (textField.getText().equals(temp_text)) {
                    textField.setText("");
                }
            }
        });
    }

    public void Text_Properties(Text text) {
        try {
            Font font = Font.loadFont(new File("res/FredokaOne-Regular.ttf").toURI().toURL().toExternalForm(), 18);
            text.setFont(font);
        } catch (IOException E) {
            System.out.println("font not found");
        }
    }

    public void create_question(Button button, Stage stage, Controller controller, Question question,
                                int index, HBox board, Scene previous, String category) {
        int width = size.width;
        int height = size.height - 80;
        VBox root = new VBox();

        HBox navBar = new HBox();
        Text return_button = new Text("<-- Return to Create Board");
        Text_Properties(return_button);
        return_button.setUnderline(true);
        navBar.getChildren().add(return_button);
        Insets insets = new Insets(20);
        navBar.setPadding(insets);
        navBar.setAlignment(Pos.TOP_LEFT);
        HBox question_creator = new HBox();
        Text text = new Text(category + " : " + button.getText() + " points");
        Text_Properties(text);
        text.setUnderline(true);
        VBox question_field = new VBox();
        Text question_text = new Text("Enter the question in the area below:");
        Text_Properties(question_text);
        TextArea question_area = new TextArea("Enter the question...");
        TextArea_properties(question_area);
        question_field.getChildren().addAll(question_text, question_area);
        Text anwser_text = new Text("Enter the answer in the area below:");
        Text_Properties(anwser_text);
        TextArea answer_area = new TextArea("Enter the answer...");
        TextArea_properties(answer_area);
        question_field.getChildren().addAll(anwser_text, answer_area);
        question_field.setAlignment(Pos.CENTER);

        VBox files_field = new VBox();
        Button image_button = new Button("Add an image");
        button_properties(image_button);
        Label audio_link = new Label("");
        try {
            Font font = Font.loadFont(new File("res/FredokaOne-Regular.ttf").toURI().toURL().toExternalForm(), 18);
            audio_link.setFont(font);
        } catch (IOException E) {
            System.out.println("font not found");
        }
        Button audio_button = new Button("Add an audio");
        button_properties(audio_button);
        ImageView imageView = new ImageView();
        File file = new File("res/no_image.jpg");
        Image image = new Image(String.valueOf(file.toURI()));
        imageView.setImage(image);
        imageView.setFitWidth(300);
        imageView.setFitHeight(200);
        files_field.getChildren().addAll(imageView, image_button, audio_link, audio_button);
        files_field.setAlignment(Pos.CENTER);
        files_field.setSpacing(20);
        files_field.setPadding(new Insets(0, 0, 0, 60));
        if (question != null) {
            question_area.setText(question.getQuestion());
            answer_area.setText(question.getAnswer());
            if (!question.getImg().matches("")) {
                file = new File(question.getImg());
                image = new Image(String.valueOf(file.toURI()));
                imageView.setImage(image);
            }
        }
        Button add_question = new Button("Save Question");
        button_properties(add_question);
        question_creator.getChildren().addAll(question_field, files_field);
        question_creator.setAlignment(Pos.CENTER);
        question_creator.setSpacing(15);
        root.getChildren().addAll(navBar, text, question_creator, add_question);
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(30);
        Scene temp = new Scene(root, width, height);

        image_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
                File file = fileChooser.showOpenDialog(null);
                File destination = new File("res/img/" + file.getName());
                if (!destination.exists()) {
                    try {
                        Files.copy(file.toPath(), destination.toPath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                Image image = null;
                image = new Image(String.valueOf(file.toURI()));
                imageView.setImage(image);
            }
        });
        audio_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Audio File", "*.mp3", "*.wav"));
                File file = fileChooser.showOpenDialog(null);
                audio_link.setText(file.getAbsolutePath());
                audio_link.setEllipsisString("...");
                audio_link.setMaxWidth(200);
            }
        });
        return_button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                return_button.setFill(Paint.valueOf("#03c6fc"));
            }
        });
        return_button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                return_button.setFill(Paint.valueOf("#000000"));
            }
        });
        return_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                question_area.setText("Enter the question...");
                answer_area.setText("Enter the answer...");
                File temp_file = new File("res/no_image.jpg");
                Image image = new Image(String.valueOf(temp_file.toURI()));
                imageView.setImage(image);
                if (question != null) {
                    question_area.setText(question.getQuestion());
                    answer_area.setText(question.getAnswer());
                    if (!question.getImg().matches("")) {
                        temp_file = new File(question.getImg());
                        image = new Image(String.valueOf(temp_file.toURI()));
                        imageView.setImage(image);
                    }
                    if (!question.getAudio().matches("")) {
                        String[] temp = question.getAudio().split("/");
                        audio_link.setText(temp[temp.length - 1]);
                    }
                }
                board.getChildren().clear();
                Question[][] temp_board = controller.get_board();
                String[] categories = controller.get_categories();
                for (int i = 0; i < temp_board.length; ++i) {
                    VBox temp_Vbox = new VBox();
                    VBox text_box = new VBox();
                    Text text = new Text(categories[i]);
                    text.setTextAlignment(TextAlignment.CENTER);
                    Text_Properties(text);
                    text.setWrappingWidth(200);
                    text_box.getChildren().add(text);
                    text_box.setMinHeight(100);
                    text_box.setMaxHeight(100);
                    text_box.setAlignment(Pos.CENTER);
                    temp_Vbox.getChildren().add(text_box);
                    for (int j = 0; j < 5; ++j) {
                        int value = (j + 1) * 100;
                        Button temp_button = new Button(Integer.toString(value));
                        if (temp_board[i][j] == null) {
                            question_not_set(temp_button);
                            create_question(temp_button, stage, controller, null, i * 5 + j, board, previous, category);
                        } else {
                            button_properties(temp_button);
                            create_question(temp_button, stage, controller, temp_board[i][j], i * 5 + j, board, previous, category);
                        }
                        temp_Vbox.getChildren().add(temp_button);
                    }
                    board.getChildren().add(temp_Vbox);
                }
                stage.setScene(previous);
                stage.show();
            }
        });
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setScene(temp);
                stage.show();
            }
        });
        add_question.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boolean cond1 = true;
                boolean cond2 = true;
                if (answer_area.getText().matches("Enter the answer...") || answer_area.getText().matches("")|| answer_area.getText().length()>200) {
                    answer_area.setStyle("-fx-border-color: #fc0303");
                    cond1 = false;
                } else {
                    if(answer_area.getText().length()>200){
                        answer_area.setStyle("-fx-border-color: #fc0303");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Answer should have less then 200 characters");
                        alert.show();
                        cond1 = false;
                    }
                    else {
                        answer_area.setStyle("-border: 0");
                    }
                }
                if (question_area.getText().matches("Enter the question...") || question_area.getText().matches("")) {
                    question_area.setStyle("-fx-border-color: #fc0303");
                    cond2 = false;
                } else {
                    if(question_area.getText().length()>200){
                        question_area.setStyle("-fx-border-color: #fc0303");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Question should have less then 200 characters");
                        alert.show();
                        cond2 = false;
                    }
                    else {
                        question_area.setStyle("-border: 0");
                    }
                }
                StringBuilder path = new StringBuilder();
                if (cond1 && cond2) {
                    button_properties(button);
                    String img_path = imageView.getImage().getUrl();
                    String audio_path = audio_link.getText();
                    String[] temp = img_path.split("/");
                    if (temp[temp.length - 1].matches("no_image.jpg")) {
                        img_path = "";
                    } else {
                        img_path = temp[temp.length - 1];
                    }
                    controller.add_question(index, question_area.getText(), answer_area.getText(), img_path, audio_path);
                    stage.setScene(previous);
                    stage.show();
                }
            }
        });
    }

    public void question_not_set(Button button) {
        button.setMinWidth(100);
        button.setMaxWidth(200);
        button.setPrefWidth(150);
        button.setStyle("-fx-background-color: linear-gradient(to bottom,#fa4343,#bf0000)");
        try {
            Font font = Font.loadFont(new File("res/FredokaOne-Regular.ttf").toURI().toURL().toExternalForm(), 18);
            button.setFont(font);
        } catch (IOException E) {
            System.out.println("font not found");
        }
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setOpacity(.5);
            }
        });
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setOpacity(1);
            }
        });
    }

    public void TextArea_properties(TextArea textArea) {
        String temp_text = textArea.getText();
        textArea.setPrefHeight(100);
        textArea.setPrefWidth(300);
        textArea.setMaxWidth(400);
        textArea.setWrapText(true);
        try {
            Font font = Font.loadFont(new File("res/FredokaOne-Regular.ttf").toURI().toURL().toExternalForm(), 18);
            textArea.setFont(font);
        } catch (IOException E) {
            System.out.println("font not found");
        }
        textArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (textArea.getText().equals(temp_text)) {
                    textArea.setText("");
                }
            }
        });
    }

    public Scene get_winner_scene(int width, int height, Stage stage) {
        VBox winner_vbox = new VBox();
        Text winner_message = new Text("Congratulation");
        Text_Properties(winner_message);
        winner_message.setFont(new Font(winner_message.getFont().getFamily(), 40));
        Text winner = new Text();
        Text_Properties(winner);
        winner.setFont(new Font(winner.getFont().getFamily(), 30));
        Text winner_score = new Text();
        Text_Properties(winner_score);
        winner_score.setFont(new Font(winner_score.getFont().getFamily(), 30));
        Button back_to_menu = new Button("Back to Main Menu");
        button_properties(back_to_menu);
        winner_vbox.getChildren().addAll(winner_message, winner, winner_score, back_to_menu);
        winner_vbox.setAlignment(Pos.CENTER);
        winner_vbox.setSpacing(40);
        back_to_menu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.reset();
                VBox root = (VBox) scene.getRoot();
                Text board_selected = (Text) root.getChildren().get(1);
                board_selected.setText("Board Selected : ");
                stage.setScene(scene);
                stage.show();
            }
        });
        return new Scene(winner_vbox, width, height);
    }

    public Scene get_player_init_scene(int width, int height, Stage stage) {
        VBox player_init_vbox = new VBox();
        Text temp_title = new Text("Players Creation");
        Text_Properties(temp_title);
        temp_title.setFont(new Font(temp_title.getFont().getFamily(), 30));
        HBox player_group = new HBox();
        VBox temp_vbox = new VBox();
        Text temp_text = new Text("Player 1");
        Text_Properties(temp_text);
        TextField username = new TextField("Enter a username...");
        TextField_properties(username, 200);
        temp_vbox.getChildren().addAll(temp_text, username);
        temp_vbox.setAlignment(Pos.CENTER);
        temp_vbox.setSpacing(20);

        VBox temp_vbox2 = new VBox();
        Text temp_text2 = new Text("Player 2");
        Text_Properties(temp_text2);
        TextField username2 = new TextField("Enter a username...");
        TextField_properties(username2, 200);
        temp_vbox2.getChildren().addAll(temp_text2, username2);
        temp_vbox2.setAlignment(Pos.CENTER);
        temp_vbox2.setSpacing(20);

        VBox temp_vbox3 = new VBox();
        Text temp_text3 = new Text("Player 3");
        Text_Properties(temp_text3);
        TextField username3 = new TextField("Enter a username...");
        TextField_properties(username3, 200);
        temp_vbox3.getChildren().addAll(temp_text3, username3);
        temp_vbox3.setAlignment(Pos.CENTER);
        temp_vbox3.setSpacing(20);

        player_group.getChildren().addAll(temp_vbox, temp_vbox2, temp_vbox3);
        player_group.setAlignment(Pos.CENTER);
        player_group.setSpacing(30);
        Button StartGame = new Button("Start Game");
        button_properties(StartGame);
        player_init_vbox.getChildren().addAll(temp_title, player_group, StartGame);
        player_init_vbox.setAlignment(Pos.CENTER);
        player_init_vbox.setSpacing(30);
        StartGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boolean cond = true;
                if(username.getText().length()>15 || username2.getText().length()>15 || username3.getText().length()>15){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Username length cannot be more then 15 character");
                    alert.show();
                    cond = false;
                }
                if(cond) {
                    Scene play_scene = get_play_scene(width, height);
                    play_scene = load_game(width, height, stage, play_scene, username, username2, username3);
                    stage.setScene(play_scene);
                    stage.show();
                }
            }
        });
        return new Scene(player_init_vbox, width, height);
    }

    public Scene get_play_scene(int width, int height) {
        VBox play_vbox = new VBox();
        Text board_title = new Text();
        Text_Properties(board_title);
        board_title.setFont(Font.font(board_title.getFont().getFamily(), 40));
        HBox play_board = new HBox();
        play_vbox.getChildren().addAll(board_title, play_board);
        play_vbox.setAlignment(Pos.TOP_CENTER);
        play_vbox.setSpacing(30);
        return new Scene(play_vbox, width, height);
    }

    public Scene load_game(int width, int height, Stage stage, Scene play_scene, TextField username, TextField username2, TextField username3) {
        VBox play_vbox = (VBox) play_scene.getRoot();
        Text board_title = (Text) play_vbox.getChildren().getFirst();
        HBox play_board = (HBox) play_vbox.getChildren().getLast();
        HBox player_group = new HBox();
        Text player_group_title = new Text("Players");
        Text_Properties(player_group_title);
        player_group_title.setFont(new Font(player_group_title.getFont().getFamily(), 30));
        play_vbox.getChildren().add(player_group_title);

        player_group.setSpacing(20);
        player_group.setAlignment(Pos.BOTTOM_CENTER);

        controller.add_player(username.getText(), username2.getText(), username3.getText());
        board_title.setText(controller.get_board_name());
        Question[][] questions = controller.get_board();
        String[] categories = controller.get_categories();
        play_board.setAlignment(Pos.CENTER);


        VBox temp_player_vbox = new VBox();
        Text temp_username = new Text(controller.players[0].getUsername());
        Text_Properties(temp_username);
        temp_username.setWrappingWidth(150);
        temp_username.setTextAlignment(TextAlignment.CENTER);
        temp_username.setFont(new Font(temp_username.getFont().getFamily(),14));
        Text points = new Text(String.valueOf(controller.players[0].getPoints()));
        Text_Properties(points);
        temp_player_vbox.getChildren().addAll(temp_username, points);
        temp_player_vbox.setSpacing(30);
        temp_player_vbox.setAlignment(Pos.CENTER);
        player_group.getChildren().add(temp_player_vbox);

        VBox temp_player_vbox2 = new VBox();
        Text temp_username2 = new Text(controller.players[1].getUsername());
        Text_Properties(temp_username2);
        temp_username2.setWrappingWidth(150);
        temp_username2.setTextAlignment(TextAlignment.CENTER);
        temp_username2.setFont(new Font(temp_username2.getFont().getFamily(),14));
        Text points2 = new Text(String.valueOf(controller.players[1].getPoints()));
        Text_Properties(points2);
        temp_player_vbox2.getChildren().addAll(temp_username2, points2);
        temp_player_vbox2.setSpacing(30);
        temp_player_vbox2.setAlignment(Pos.CENTER);
        player_group.getChildren().add(temp_player_vbox2);

        VBox temp_player_vbox3 = new VBox();
        Text temp_username3 = new Text(controller.players[2].getUsername());
        Text_Properties(temp_username3);
        temp_username3.setWrappingWidth(150);
        temp_username3.setTextAlignment(TextAlignment.CENTER);
        temp_username3.setFont(new Font(temp_username3.getFont().getFamily(),14));
        Text points3 = new Text(String.valueOf(controller.players[2].getPoints()));
        Text_Properties(points3);
        temp_player_vbox3.getChildren().addAll(temp_username3, points3);
        temp_player_vbox3.setSpacing(30);
        temp_player_vbox3.setAlignment(Pos.CENTER);
        player_group.getChildren().add(temp_player_vbox3);


        for (int i = 0; i < questions.length; ++i) {
            VBox column = new VBox();
            Button category_name = new Button(categories[i]);
            try {
                Font font = Font.loadFont(new File("res/FredokaOne-Regular.ttf").toURI().toURL().toExternalForm(), 20);
                category_name.setFont(font);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            category_name.setStyle("-fx-text-fill: #ffffff; -fx-background-color:  #088abd");
            category_name.setMinWidth(200);
            category_name.setMinHeight(75);
            column.getChildren().add(category_name);
            for (int j = 0; j < 5; ++j) {
                Question cur_question = questions[i][j];
                int value = (j + 1) * 100;
                Button temp_question = new Button(String.valueOf(value));
                try {
                    Font font = Font.loadFont(new File("res/FredokaOne-Regular.ttf").toURI().toURL().toExternalForm(), 20);
                    temp_question.setFont(font);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                temp_question.setStyle("-fx-text-fill: #ffffff; -fx-background-color:  #2fbff7;-fx-border-width: 5;-fx-border-color: #049bd6;-fx-text-fill: #faf19d");
                temp_question.setMinWidth(200);
                temp_question.setMinHeight(75);
                column.getChildren().add(temp_question);

                VBox question_vbox = new VBox();
                Text question = new Text(cur_question.getQuestion());
                Text_Properties(question);
                if(question.getText().length()<100) {
                    question.setFont(new Font(question.getFont().getFamily(), 30));
                }
                question.setWrappingWidth(1000);
                question.setTextAlignment(TextAlignment.CENTER);
                Text answer = new Text(cur_question.getAnswer());
                Text_Properties(answer);
                answer.setWrappingWidth(1000);
                answer.setTextAlignment(TextAlignment.CENTER);
                if(answer.getText().length()<50) {
                    answer.setFont(new Font(answer.getFont().getFamily(), 30));
                }
                answer.setOpacity(0);
                ImageView imageView = new ImageView();
                if (!questions[i][j].getImg().matches("")) {
                    File file = new File(URLDecoder.decode("res/img/" + cur_question.getImg(), StandardCharsets.UTF_8));
                    Image image = new Image(String.valueOf(file.toURI()));
                    imageView.setImage(image);
                    imageView.setFitHeight(300);
                    imageView.setFitWidth(500);
                }
                VBox audio = new VBox();
                if (!cur_question.getAudio().isEmpty()) {
                    Button play_audio = new Button("Play_audio");
                    button_properties(play_audio);
                    File file = new File(URLDecoder.decode(cur_question.getAudio(), StandardCharsets.UTF_8));
                    Media media = new Media(file.toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    audio.getChildren().addAll(play_audio);
                    play_audio.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            mediaPlayer.play();
                            mediaPlayer.seek(Duration.ZERO);
                        }
                    });
                }
                Text player_question_title = new Text("Players");
                Text_Properties(player_question_title);
                player_question_title.setFont(new Font(player_question_title.getFont().getFamily(), 30));
                HBox player_question_group = new HBox();
                HBox footer = new HBox();
                VBox reveal_vbox = new VBox();
                Button reveal = new Button("Reveal");
                button_properties(reveal);
                reveal_vbox.getChildren().addAll(reveal);
                reveal_vbox.setAlignment(Pos.CENTER_LEFT);
                reveal_vbox.setPadding(new Insets(20, 20, 0, 20));
                for (int k = 0; k < 3; ++k) {
                    Player temp_user = controller.players[k];
                    VBox temp_player = new VBox();
                    Text temp_Username = new Text(temp_user.getUsername());
                    Text_Properties(temp_Username);
                    temp_Username.setFont(new Font(temp_Username.getFont().getFamily(),14));
                    temp_Username.setWrappingWidth(150);
                    temp_Username.setTextAlignment(TextAlignment.CENTER);
                    HBox point_box = new HBox();
                    Text minus = new Text("-");
                    Text_Properties(minus);
                    minus.setFont(new Font(minus.getFont().getFamily(), 30));
                    Text plus = new Text("+");
                    Text_Properties(plus);
                    plus.setFont(new Font(plus.getFont().getFamily(), 30));
                    Text temp_points = new Text(String.valueOf(temp_user.getPoints()));
                    Text_Properties(temp_points);
                    temp_points.setFont(new Font(temp_points.getFont().getFamily(), 30));
                    point_box.getChildren().addAll(plus, temp_points, minus);
                    point_box.setAlignment(Pos.CENTER);
                    point_box.setSpacing(20);
                    temp_player.getChildren().addAll(temp_Username, point_box);
                    temp_player.setAlignment(Pos.CENTER);
                    temp_player.setSpacing(20);
                    temp_player.setMaxHeight(100);
                    temp_player.setMinHeight(100);
                    temp_player.setPadding(new Insets(0, 0, 20, 0));
                    player_question_group.getChildren().add(temp_player);
                    minus.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            minus.setFill(Paint.valueOf("#03c6fc"));
                        }
                    });
                    minus.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            minus.setFill(Paint.valueOf("#000000"));
                        }
                    });
                    minus.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            controller.update_score(temp_user, value * -1);
                            temp_points.setText(String.valueOf(temp_user.getPoints()));
                        }
                    });
                    plus.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            plus.setFill(Paint.valueOf("#03c6fc"));
                        }
                    });
                    plus.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            plus.setFill(Paint.valueOf("#000000"));
                        }
                    });
                    plus.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            controller.update_score(temp_user, value);
                            temp_points.setText(String.valueOf(temp_user.getPoints()));
                        }
                    });
                }
                BorderPane borderPane = new BorderPane();
                footer.setMinWidth(width);
                footer.setMaxWidth(width);
                footer.setPrefWidth(width);
                reveal_vbox.setPadding(new Insets(0, width / 2 - 455, 0, 20));
                player_question_group.setSpacing(60);
                player_question_group.setAlignment(Pos.CENTER);
                audio.setAlignment(Pos.CENTER);
                VBox final_footer = new VBox();
                footer.getChildren().addAll(reveal_vbox, player_question_group);
                final_footer.getChildren().addAll(player_question_title, footer);
                final_footer.setAlignment(Pos.CENTER);
                footer.setAlignment(Pos.BOTTOM_LEFT);
                question_vbox.getChildren().addAll(question, imageView, audio, answer);
                question_vbox.setAlignment(Pos.CENTER);
                question_vbox.setSpacing(40);
                borderPane.setCenter(question_vbox);
                borderPane.setBottom(final_footer);
                borderPane.setStyle("-fx-background-color: #6da1fc");
                Scene question_scene = new Scene(borderPane, width, height);
                reveal.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (answer.getOpacity() <= 0.3) {
                            reveal.setText("Next");
                            answer.setOpacity(1);
                        } else {
                            points.setText(String.valueOf(controller.players[0].getPoints()));
                            points2.setText(String.valueOf(controller.players[1].getPoints()));
                            points3.setText(String.valueOf(controller.players[2].getPoints()));
                            temp_question.setText("");
                            if (!cur_question.isDone()) {
                                cur_question.setDone(true);
                                controller.update_done();
                            }
                            if (controller.get_done() == questions.length * 5) {
                                StringBuilder temp_winner = new StringBuilder();
                                int temp_point = Integer.MIN_VALUE;
                                for (int j = 0; j < 3; ++j) {
                                    Player temp_player = controller.players[j];
                                    if (temp_player.getPoints() > temp_point) {
                                        temp_winner = new StringBuilder(temp_player.getUsername());
                                        temp_point = temp_player.getPoints();
                                    } else if (temp_player.getPoints() == temp_point) {
                                        temp_winner.append(" , ").append(temp_player.getUsername());
                                    }
                                }
                                play_vbox.getChildren().clear();
                                Scene victory_scene = get_winner_scene(width, height, stage);
                                VBox temp_vbox = (VBox) victory_scene.getRoot();
                                Text winner = (Text) temp_vbox.getChildren().get(1);
                                winner.setText(temp_winner.toString());
                                Text winner_score = (Text) temp_vbox.getChildren().get(2);
                                winner_score.setText(String.valueOf(temp_point));
                                stage.setScene(victory_scene);
                                stage.show();
                            } else {
                                stage.setScene(play_scene);
                                stage.show();
                            }
                        }
                    }
                });
                temp_question.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        for (int i = 0; i < 3; ++i) {
                            VBox temp = (VBox) player_question_group.getChildren().get(i);
                            HBox temp_box = (HBox) temp.getChildren().get(1);
                            Text score = (Text) temp_box.getChildren().get(1);
                            score.setText(String.valueOf(controller.players[i].getPoints()));
                        }
                        stage.setScene(question_scene);
                        stage.show();
                    }
                });
                temp_question.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        temp_question.setOpacity(.5);
                    }
                });
                temp_question.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        temp_question.setOpacity(1);
                    }
                });
            }
            play_board.getChildren().add(column);
        }

        play_vbox.getChildren().add(player_group);
        return play_scene;
    }

    public void play_audio(File file) {
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}
