package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import model.Blockchain;
import model.Miner;

public class BlockchainMiningGUI extends JFrame {
  private JLabel winnerLabel;
  private JLabel difficultyLabel;
  private JTable minersTable;
  private DefaultTableModel minersTableModel;
  private JTable confirmingMinersTable;
  private JLabel competitionStatusLabel;
  private ExecutorService executor;
  private Blockchain blockchain;

  public BlockchainMiningGUI(Blockchain blockchain) {
    this.blockchain = blockchain;
    this.executor = Executors.newFixedThreadPool(10); // Pool de hilos para los mineros
    setupUI();
  }

  private void setupUI() {
    setTitle("Simulación de Minería Blockchain");
    setSize(800, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Tema oscuro
    Color bgColor = new Color(30, 30, 30);
    Color textColor = new Color(230, 230, 230);
    Color panelColor = new Color(45, 45, 45);
    Color buttonColor = new Color(50, 50, 50);
    Color buttonTextColor = new Color(255, 255, 255);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(bgColor);
    mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
    setContentPane(mainPanel);

    // Tabla de Mineros (lado izquierdo)
    String[] columnNames = {"ID del Minero", "Estado"};
    minersTableModel = new DefaultTableModel(columnNames, 0);
    minersTable = new JTable(minersTableModel);
    minersTable.setBackground(panelColor);
    minersTable.setForeground(textColor);
    minersTable.setGridColor(new Color(70, 70, 70));
    minersTable.setFillsViewportHeight(true);
    minersTable.setFont(new Font("Arial", Font.PLAIN, 14));
    minersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
    minersTable.getTableHeader().setForeground(textColor);
    minersTable.getTableHeader().setBackground(buttonColor);
    minersTable.setRowHeight(25);
    JScrollPane minersScrollPane = new JScrollPane(minersTable);
    minersScrollPane.setBorder(BorderFactory.createEmptyBorder());

    // Panel central (proceso de competencia)
    JPanel competitionPanel = new JPanel();
    competitionPanel.setBackground(panelColor);
    competitionPanel.setLayout(new BorderLayout());
    competitionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(textColor), "Proceso de Competencia", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), textColor));
    competitionStatusLabel = new JLabel("Minería en progreso...");
    competitionStatusLabel.setForeground(textColor);
    competitionStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
    competitionPanel.add(competitionStatusLabel, BorderLayout.CENTER);

    // Tabla de confirmación de mineros (lado derecho)
    DefaultTableModel confirmingMinersModel = new DefaultTableModel(new String[]{"Mineros Confirmando"}, 0);
    confirmingMinersTable = new JTable(confirmingMinersModel);
    confirmingMinersTable.setBackground(panelColor);
    confirmingMinersTable.setForeground(textColor);
    confirmingMinersTable.setFont(new Font("Arial", Font.PLAIN, 14));
    confirmingMinersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
    confirmingMinersTable.getTableHeader().setForeground(textColor);
    confirmingMinersTable.getTableHeader().setBackground(buttonColor);
    confirmingMinersTable.setRowHeight(25);
    confirmingMinersTable.setFillsViewportHeight(true);

    JScrollPane confirmingScrollPane = new JScrollPane(confirmingMinersTable);
    confirmingScrollPane.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(textColor),
        "Mineros Confirmando",
        TitledBorder.CENTER,
        TitledBorder.TOP,
        new Font("Arial", Font.BOLD, 16),
        textColor
    ));
    confirmingScrollPane.setBackground(panelColor);

    // Panel de dificultad (parte superior)
    JPanel difficultyPanel = new JPanel();
    difficultyPanel.setBackground(bgColor);
    difficultyPanel.setLayout(new BorderLayout());
    difficultyLabel = new JLabel("Dificultad: " + Miner.DIFFICULTY);
    difficultyLabel.setForeground(textColor);
    difficultyLabel.setHorizontalAlignment(SwingConstants.CENTER);
    difficultyPanel.add(difficultyLabel, BorderLayout.CENTER);

    // Panel de Ganador (parte inferior)
    JPanel winnerPanel = new JPanel();
    winnerPanel.setBackground(bgColor);
    winnerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(textColor), "Minero Ganador", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), textColor));
    winnerLabel = new JLabel("Esperando resultado...");
    winnerLabel.setForeground(textColor);
    winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    winnerPanel.add(winnerLabel);

    // Botones con tema negro
    JButton startMiningButton = new JButton("Iniciar Minería");
    startMiningButton.setBackground(buttonColor);
    startMiningButton.setForeground(buttonTextColor);
    startMiningButton.setBorder(BorderFactory.createEmptyBorder());
    startMiningButton.addActionListener(e -> startMining());

    JButton resetButton = new JButton("Reiniciar");
    resetButton.setBackground(buttonColor);
    resetButton.setForeground(buttonTextColor);
    resetButton.setBorder(BorderFactory.createEmptyBorder());
    resetButton.addActionListener(e -> resetMining());

    // Añadir los botones al panel superior
    difficultyPanel.add(startMiningButton, BorderLayout.WEST);
    difficultyPanel.add(resetButton, BorderLayout.EAST);

    // Añadir componentes
    mainPanel.add(minersScrollPane, BorderLayout.WEST);
    mainPanel.add(competitionPanel, BorderLayout.CENTER);
    mainPanel.add(confirmingScrollPane, BorderLayout.EAST);
    mainPanel.add(difficultyPanel, BorderLayout.NORTH);
    mainPanel.add(winnerPanel, BorderLayout.SOUTH);

    // Mostrar la ventana
    setVisible(true);
  }

  public void addMinerAttempt(String minerId, String status) {
    SwingUtilities.invokeLater(() -> {
      boolean minerExists = false;
      for (int i = 0; i < minersTableModel.getRowCount(); i++) {
        if (minersTableModel.getValueAt(i, 0).equals(minerId)) {
          minersTableModel.setValueAt(status, i, 1);
          minerExists = true;
          break;
        }
      }
      if (!minerExists) {
        minersTableModel.addRow(new Object[]{minerId, status});
      }
    });
  }

  public void addConfirmingMiner(String minerInfo) {
    SwingUtilities.invokeLater(() -> {
      DefaultTableModel model = (DefaultTableModel) confirmingMinersTable.getModel();
      model.setRowCount(0); // Limpiar la tabla actual
      for (int i = 0; i < minersTableModel.getRowCount(); i++) {
        String currentMiner = (String) minersTableModel.getValueAt(i, 0);
        if (!currentMiner.equals(minerInfo)) {
          model.addRow(new Object[]{currentMiner});
        }
      }
      updateCompetitionStatus("El minero " + minerInfo + " ganó la competencia.");
    });
  }

  public void updateCompetitionStatus(String status) {
    SwingUtilities.invokeLater(() -> competitionStatusLabel.setText(status));
  }

  public void updateWinner(String winnerInfo) {
    SwingUtilities.invokeLater(() -> winnerLabel.setText("Ganador: " + winnerInfo));
  }

  public void resetMining() {
    executor.shutdownNow();
    minersTableModel.setRowCount(0);
    ((DefaultTableModel) confirmingMinersTable.getModel()).setRowCount(0);
    updateWinner("Esperando resultado...");
    updateCompetitionStatus("Minería reiniciada. Haz clic en 'Iniciar Minería' para comenzar de nuevo.");
  }

  public void startMining() {
    executor.shutdownNow();  // Detener cualquier ejecución anterior
    executor = Executors.newFixedThreadPool(10);  // Crear un nuevo pool de hilos

    for (int i = 1; i <= 10; i++) {
      Miner miner = new Miner(blockchain, "Minero " + i);
      miner.setGUI(this);

      executor.submit(() -> {
        miner.mineBlock();  // Ejecutar el proceso de minería

        // Verificar si el minero encontró un bloque válido
        if (miner.isSuccessful()) {
          SwingUtilities.invokeLater(() -> {
            addMinerAttempt(miner.getMinerId(), "Éxito");
            addConfirmingMiner(miner.getMinerId());
            updateWinner(miner.getMinerId());  // Mostrar el ganador
          });
        } else {
          SwingUtilities.invokeLater(() -> addMinerAttempt(miner.getMinerId(), "Fallo"));
        }
      });
    }
    updateCompetitionStatus("Minería en progreso...");
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      Blockchain blockchain = new Blockchain();
      BlockchainMiningGUI gui = new BlockchainMiningGUI(blockchain);
      gui.startMining();
    });
  }
}