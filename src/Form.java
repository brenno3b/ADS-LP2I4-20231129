import javax.swing.*;
import java.awt.*;

public class Form extends JFrame {
    private final JTextField campoMaximo;
    private final JTextField campoPrioridade1;
    private final JTextField campoPrioridade2;
    private final DefaultListModel<String> listaModel1;
    private final DefaultListModel<String> listaModel2;
    private final JLabel labelFim1;
    private final JLabel labelFim2;

    public Form() {
        setTitle("Trabalho prático 05");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JLabel labelMaximo = new JLabel("Máximo:");
        JLabel labelPrioridade1 = new JLabel("Prioridade 1:");
        JLabel labelPrioridade2 = new JLabel("Prioridade 2:");

        labelFim1 = new JLabel("Final");
        labelFim1.setHorizontalAlignment(SwingConstants.CENTER);
        labelFim2 = new JLabel("Final");
        labelFim2.setHorizontalAlignment(SwingConstants.CENTER);

        labelFim1.setVisible(false);
        labelFim2.setVisible(false);

        campoMaximo = new JTextField(10);
        campoPrioridade1 = new JTextField(10);
        campoPrioridade2 = new JTextField(10);

        listaModel1 = new DefaultListModel<>();
        listaModel2 = new DefaultListModel<>();

        JList<String> lista1 = new JList<>(listaModel1);
        JList<String> lista2 = new JList<>(listaModel2);

        JButton botaoIniciar = new JButton("Iniciar");
        botaoIniciar.addActionListener(e -> iniciarThreads());

        JButton botaoLimpar = new JButton("Limpar");
        botaoLimpar.addActionListener(e -> limparListas());

        setLayout(new BorderLayout());

        JPanel painelFormThread = new JPanel(new GridLayout(3, 2, 5, 5));
        painelFormThread.add(labelMaximo);
        painelFormThread.add(campoMaximo);
        painelFormThread.add(labelPrioridade1);
        painelFormThread.add(campoPrioridade1);
        painelFormThread.add(labelPrioridade2);
        painelFormThread.add(campoPrioridade2);

        JPanel painelButtonThread = new JPanel();
        painelButtonThread.add(botaoIniciar);

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.add(painelFormThread, BorderLayout.NORTH);
        painelSuperior.add(painelButtonThread, BorderLayout.SOUTH);

        add(painelSuperior, BorderLayout.NORTH);

        JPanel painelFim1 = new JPanel(new BorderLayout());
        painelFim1.add(new JScrollPane(lista1), BorderLayout.NORTH);
        painelFim1.add(labelFim1, BorderLayout.CENTER);

        JPanel painelFim2 = new JPanel(new BorderLayout());
        painelFim2.add(new JScrollPane(lista2), BorderLayout.NORTH);
        painelFim2.add(labelFim2, BorderLayout.CENTER);

        JPanel painelCentral = new JPanel(new GridLayout(1, 2, 10, 10));
        painelCentral.add(painelFim1);
        painelCentral.add(painelFim2);
        add(painelCentral, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel();
        painelInferior.add(botaoLimpar);
        add(painelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void iniciarThreads() {
        int max = Integer.parseInt(campoMaximo.getText());
        int priority01 = Integer.parseInt(campoPrioridade1.getText());
        int priority02 = Integer.parseInt(campoPrioridade2.getText());

        Thread thread01 = new Thread(new RunnableTask(listaModel1, max, labelFim1));
        thread01.setPriority(priority01);

        Thread thread02 = new Thread(new RunnableTask(listaModel2, max, labelFim2));
        thread02.setPriority(priority02);

        thread01.start();
        thread02.start();
    }

    private void limparListas() {
        listaModel1.clear();
        listaModel2.clear();

        labelFim1.setVisible(false);
        labelFim2.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Form form = new Form();
            form.setVisible(true);
        });
    }

    private static class RunnableTask implements Runnable {
        private final DefaultListModel<String> listModel;
        private final int max;
        private final JLabel label;

        public RunnableTask(DefaultListModel<String> listModel, int max, JLabel label) {
            this.listModel = listModel;
            this.max = max;
            this.label = label;
        }

        @Override
        public void run() {
            for (int i = 0; i < max; i++) {
                listModel.addElement(String.valueOf(i));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            label.setVisible(true);
        }
    }
}