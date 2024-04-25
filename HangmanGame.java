import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HangmanGame extends JFrame implements ActionListener {
private String[] words = {"pera", "banana", "laranja", "camisa","bermuda", "calça", "oculos","cachorro","gato","frances fudido","madonna","kanye west","imagine dragons","aquariano nato","eu","tentei","gay","hetero","samsung","mouse","teclado","monitor","apple","rico","pobre","ventilador","pizza", "vodka", "hamburguer","gin","sushi","bebacatu","absinto"};
    private String wordToGuess;
    private int guessesLeft = 5;
    private StringBuilder hiddenWord;

    private JLabel hiddenWordLabel;
    private JLabel guessesLeftLabel;
    private JTextField guessTextField;
    private JTextField guessWordField; 
    private JButton guessButton;
    private JButton guessWordButton; 

    public HangmanGame() {
        setTitle("Hangman Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Color.CYAN); // Define a cor de fundo

        hiddenWordLabel = new JLabel();
        hiddenWordLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Define o estilo da fonte
        hiddenWordLabel.setForeground(Color.BLACK); // Define a cor do texto

        guessesLeftLabel = new JLabel("Guesses Left: " + guessesLeft);
        guessesLeftLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Define o estilo da fonte
        guessesLeftLabel.setForeground((new Color(200,0,0))); // Define a cor do texto

        guessTextField = new JTextField(10);
        guessTextField.setFont(new Font("Arial", Font.PLAIN, 16)); // Define o estilo da fonte

        guessButton = new JButton("Guess!");
        guessButton.addActionListener(this);
        guessButton.setFont(new Font("Arial", Font.BOLD, 16)); // Define o estilo da fonte
        guessButton.setBackground(Color.GRAY); // Define a cor de fundo do botão
        guessButton.setForeground(Color.BLACK); // Define a cor do texto do botão

        guessWordField = new JTextField(10);
        guessWordButton = new JButton("Guess Word!");
        guessWordButton.addActionListener(this);
        guessWordButton.setFont(new Font("Arial", Font.BOLD, 16)); // Define o estilo da fonte
        guessWordButton.setBackground(new Color(199,5,214)); // Define a cor de fundo do botão
        guessWordButton.setForeground(Color.BLACK); // Define a cor do texto do botão

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1, 10, 10)); // Layout de grade com 6 linhas e 1 coluna
        mainPanel.setBackground(new Color(4, 124, 204)); // Define a cor de fundo do painel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Adiciona margem ao redor do painel
        mainPanel.add(hiddenWordLabel);
        mainPanel.add(guessesLeftLabel);
        mainPanel.add(guessTextField);
        mainPanel.add(guessButton);
        mainPanel.add(guessWordField);
        mainPanel.add(guessWordButton);

        getContentPane().add(mainPanel);
        initializeGame();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeGame() {
        wordToGuess = words[(int) (Math.random() * words.length)];
        hiddenWord = new StringBuilder("_".repeat(wordToGuess.length()));
        hiddenWordLabel.setText(hiddenWord.toString());
        guessesLeft = 6;
        guessesLeftLabel.setText("Guesses Left: " + guessesLeft);
    }

    private void updateHiddenWord(char guess) {
        boolean found = false;
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == guess) {
                hiddenWord.setCharAt(i, guess);
                found = true;
            }
        }
        hiddenWordLabel.setText(hiddenWord.toString());
        if (!found) {
            guessesLeft--;
            guessesLeftLabel.setText("Guesses Left: " + guessesLeft);
            if (guessesLeft == 0) {
                endGame("You Lose! The word was " + wordToGuess);
            }
        } else if (hiddenWord.toString().equals(wordToGuess)) {
            endGame("Congratulations! You won!");
        }
    }

    private void guessWord(String guess) {
        if (guess.equalsIgnoreCase(wordToGuess)) {
            endGame("Congratulations! You won!");
        } else {
            guessesLeft--;
            guessesLeftLabel.setText("Guesses Left: " + guessesLeft);
            if (guessesLeft == 0) {
                endGame("You Lose! The word was " + wordToGuess);
            }
        }
    }

    private void endGame(String message) {
        guessTextField.setEnabled(false);
        guessButton.setEnabled(false);
        guessWordField.setEnabled(false); 
        guessWordButton.setEnabled(false); 
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        initializeGame();
        guessTextField.setEnabled(true);
        guessButton.setEnabled(true);
        guessWordField.setEnabled(true); 
        guessWordField.setText(""); 
        guessWordButton.setEnabled(true); 
        guessTextField.requestFocus();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guessButton || e.getSource() == guessTextField) {
            String guessText = guessTextField.getText();
            if (guessText.length() > 0) {
                char guess = guessText.charAt(0);
                updateHiddenWord(guess);
                guessTextField.setText("");
            }
        } else if (e.getSource() == guessWordButton) {
            String guess = guessWordField.getText();
            if (guess.length() > 0) {
                guessWord(guess);
                guessWordField.setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HangmanGame::new);
    }
}
