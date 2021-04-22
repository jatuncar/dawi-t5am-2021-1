package org.ciberfarma.app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.ciberfarma.modelo.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class FrmCrudUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextArea txtS;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmCrudUsuario frame = new FrmCrudUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmCrudUsuario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 453, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrar();
			}
		});
		btnRegistrar.setBounds(338, 23, 89, 23);
		contentPane.add(btnRegistrar);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(338, 57, 89, 23);
		contentPane.add(btnBuscar);
		
		JLabel lblNewLabel = new JLabel("Código : ");
		lblNewLabel.setBounds(10, 27, 61, 14);
		contentPane.add(lblNewLabel);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(89, 24, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre :");
		lblNombre.setBounds(10, 60, 61, 14);
		contentPane.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(89, 57, 86, 20);
		contentPane.add(txtNombre);
		
		JLabel lblApellido = new JLabel("Apellido :");
		lblApellido.setBounds(10, 88, 61, 14);
		contentPane.add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(89, 85, 86, 20);
		contentPane.add(txtApellido);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 183, 417, 140);
		contentPane.add(scrollPane);
		
		txtS = new JTextArea();
		scrollPane.setViewportView(txtS);
		
		JButton btnListado = new JButton("Listado");
		btnListado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listado();
			}
		});
		btnListado.setBounds(338, 149, 89, 23);
		contentPane.add(btnListado);
	}
	
	void registrar() {
		String nombre = leerNombre();
		
	}

	

	void listado() {
		// Obtener un listado de los Usuarios
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager em = fabrica.createEntityManager();
		
		//TypedQuery<Usuario> consulta = em.createNamedQuery("Usuario.findAll", Usuario.class);
		//List<Usuario> lstUsuarios = consulta.getResultList();
		
		TypedQuery<Usuario> consulta = em.createNamedQuery("Usuario.findAllWithType", Usuario.class);
		consulta.setParameter("xtipo", 1);
		List<Usuario> lstUsuarios = consulta.getResultList();
		em.close();
		
		// pasar el listado a txt,..
		for (Usuario u : lstUsuarios) {
			txtS.append(u.getCodigo() + "\t" + u.getNombre() +
					"\t" + u.getApellido() + "\n");
		}	
	}

	void buscar() {
		// leer el código
		int codigo = leerCodigo();
		// buscar en la tabla, para obtener un Usuario
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager em = fabrica.createEntityManager();
		
		Usuario u = em.find(Usuario.class, codigo);
		em.close();
		// si existe lo muestra en los campos, sino avisa
		if (u == null) {
			aviso("Usuario " + codigo + " No existe!!!");
		} else {
			txtNombre.setText(u.getNombre());
			txtApellido.setText(u.getApellido());
		}
	}

	private void aviso(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Aviso del sistema", 2);
		
	}

	private String leerNombre() {
		if (!txtNombre.getText().matches("")) {
			return null;
		}
		return txtNombre.getText();
	}
	
	private int leerCodigo() {
		return Integer.parseInt(txtCodigo.getText());
	}
}
