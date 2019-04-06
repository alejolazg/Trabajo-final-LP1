package co.edu.cur.practica.vistas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import co.edu.cur.practica.implementaciones.TransaccionMYSQL;
import co.edu.cur.practica.implementaciones.TransaccionSQLSERVER;
import co.edu.cur.practica.interfaces.ITransaccion;


public class PantallaEstudiantes {

	private JFrame frame;
	private JTextField txtCodigoEstudiante;
	private JTextField txtNombre;
	private JTextField txtDireccion;

	/**
	 * Launch the application.
	 */
	
	PreparedStatement ps = null;
	ResultSet rs = null;
	int retorno = 0;	
	
	private void limpiarcuadrotxt() {
		txtCodigoEstudiante.setText(null);
		txtNombre.setText(null);
		txtDireccion.setText(null);
	}
		
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaEstudiantes window = new PantallaEstudiantes();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PantallaEstudiantes() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.BLACK);
		frame.getContentPane().setBackground(new Color(255, 102, 0));
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 12));
		frame.setBounds(100, 100, 495, 377);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblConexionBdMysql = new JLabel("MAESTRO ESTUDIANTES (BD MYSQL y SQL)");
		lblConexionBdMysql.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblConexionBdMysql.setBounds(79, 11, 320, 20);
		frame.getContentPane().add(lblConexionBdMysql);
		
		JLabel lbCodigoEstudiante = new JLabel("Codigo Estudiante");
		lbCodigoEstudiante.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbCodigoEstudiante.setBounds(34, 55, 117, 23);
		frame.getContentPane().add(lbCodigoEstudiante);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNombre.setBounds(69, 89, 76, 14);
		frame.getContentPane().add(lblNombre);
		
		JLabel lblDireccion = new JLabel("Direccion");
		lblDireccion.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDireccion.setBounds(69, 123, 76, 14);
		frame.getContentPane().add(lblDireccion);
		
		txtCodigoEstudiante = new JTextField();
		txtCodigoEstudiante.setForeground(Color.BLACK);
		txtCodigoEstudiante.setBounds(158, 56, 86, 20);
		frame.getContentPane().add(txtCodigoEstudiante);
		txtCodigoEstudiante.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(155, 87, 225, 20);
		frame.getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		txtDireccion = new JTextField();
		txtDireccion.setBounds(158, 121, 225, 20);
		frame.getContentPane().add(txtDireccion);
		txtDireccion.setColumns(10);
		
		//Boton Consultar//
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ITransaccion transaccion= new TransaccionMYSQL();
					Connection con = transaccion.conectar();
					String sql = "select Nombre,Direccion from estudiantes where CodigoEstudiante = ?";
					ps = con.prepareStatement(sql);
					ps.setString(1, txtCodigoEstudiante.getText());
					 ResultSet rs = ps.executeQuery();
					if(rs.next()) {
						//txtCodigoEstudiante.setText(rs.getString ("CodigoEstudiante"));//
						txtNombre.setText(rs.getString ("Nombre"));
						txtDireccion.setText(rs.getString ("Direccion"));
					}else {
						rs.close();
						ps.close();
						con.close();
						 transaccion= new TransaccionSQLSERVER();
						 con = transaccion.conectar();
						 sql = "select Nombre, Direccion from estudiantes where CodigoEstudiante = ?";
						ps = con.prepareStatement(sql);
						ps.setString(1, txtCodigoEstudiante.getText());
						 rs = ps.executeQuery();
						if(rs.next()) {
							//txtCodigoEstudiante.setText(rs.getString ("CodigoEstudiante"));
							txtNombre.setText(rs.getString ("Nombre"));
							txtDireccion.setText(rs.getString ("Direccion"));
						}else {
						
						JOptionPane.showMessageDialog(null, "Documento de Persona no Encontrado");
						}
						rs.close();
						ps.close();
						con.close();
					}
					con.close();
			}catch (SQLException e1){
				JOptionPane.showMessageDialog(null,"Error al Acceder a BD");
				e1.printStackTrace();
			}
			}	
		});
		btnConsultar.setBounds(34, 180, 89, 23);
		frame.getContentPane().add(btnConsultar);
		
		//boton agregar//
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBackground(Color.WHITE);
		btnAgregar.setForeground(new Color(0, 51, 0));
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ITransaccion transaccionMySQL = new TransaccionMYSQL();
					Connection con = transaccionMySQL.conectar();
					ITransaccion transaccionSQLSERVER = new TransaccionSQLSERVER();
					Connection conSQLServer = transaccionSQLSERVER.conectar();
					String sql = "insert into estudiantes(CodigoEstudiante,Nombre,Direccion)values(?,?,?)";
				
					ps = con.prepareStatement(sql);
					ps.setString(1, txtCodigoEstudiante.getText());
					ps.setString(2, txtNombre.getText());
					ps.setString(3, txtDireccion.getText());
					retorno = ps.executeUpdate();
					if(retorno >0 ) {
						JOptionPane.showMessageDialog(null,"Registro Agregado Correctamente en MYSQL" );
						
				
					} else {
						JOptionPane.showMessageDialog(null,"No se Pudo Agregar el Registro" );
					}
					ps.close();
					con.close();
					String sqlSQLServer = "insert into estudiantes(CodigoEstudiante,Nombre,Direccion)values(?,?,?)";
					ps = conSQLServer.prepareStatement(sqlSQLServer);
					ps.setString(1, txtCodigoEstudiante.getText());
					ps.setString(2, txtNombre.getText());
					ps.setString(3, txtDireccion.getText());
					retorno = ps.executeUpdate();
					if(retorno >0 ) {
						JOptionPane.showMessageDialog(null,"Registro Agregado Correctamente en SQLServer" );
						limpiarcuadrotxt();
				
					} else {
						JOptionPane.showMessageDialog(null,"No se Pudo Agregar el Registro en sql server" );
					}
					ps.close();
					con.close();
					
				}
				catch (SQLException e1){
					JOptionPane.showMessageDialog(null,"Error al Acceder a BD");
					e1.printStackTrace();
				}
			}

			
		});
		btnAgregar.setBounds(200, 180, 89, 23);
		frame.getContentPane().add(btnAgregar);
		
		
		//Boton salir//
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				System.exit(0);
			}
		});
		btnSalir.setBounds(281, 236, 69, 23);
		frame.getContentPane().add(btnSalir);
		
		//Boton Borrar//
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ITransaccion transaccionMySQL = new TransaccionMYSQL();
					Connection con = transaccionMySQL.conectar();
					ITransaccion transaccionSQLSERVER = new TransaccionSQLSERVER();
					Connection conSQLServer = transaccionSQLSERVER.conectar();
		
					String sql = ("delete from estudiantes where CodigoEstudiante = ?");
					ps = con.prepareStatement(sql);
					ps.setString(1, txtCodigoEstudiante.getText());
					retorno = ps.executeUpdate();
					if(retorno >0 ) {
						JOptionPane.showMessageDialog(null,"Registro Borrado Correctamente my sql" );
						
					ps.close();
					con.close();
					} else {
						JOptionPane.showMessageDialog(null,"No se Pudo Borrar el Registro my sql" );
					}


					String sqlSQLServer = ("delete from estudiantes where CodigoEstudiante = ?");
						ps = conSQLServer.prepareStatement(sqlSQLServer);
						ps.setString(1, txtCodigoEstudiante.getText());
						retorno = ps.executeUpdate();
						if(retorno >0 ) {
							JOptionPane.showMessageDialog(null,"Registro borrado Correctamente en SQLServer" );
							ps.close();
							con.close();
						} else {
							JOptionPane.showMessageDialog(null,"No se Pudo borrar el Registro en sql server" );
						}

				}
				catch (SQLException e1){
					JOptionPane.showMessageDialog(null,"Error al Acceder a BD");
					e1.printStackTrace();
				}
			}
			
		});
		btnBorrar.setBounds(102, 236, 89, 23);
		frame.getContentPane().add(btnBorrar);

		
		//Boton Actualizar//
		
		JButton button = new JButton("Actualizar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ITransaccion transaccionMySQL = new TransaccionMYSQL();
					Connection con = transaccionMySQL.conectar();
					ITransaccion transaccionSQLSERVER = new TransaccionSQLSERVER();
					Connection conSQLServer = transaccionSQLSERVER.conectar();
					
					String sql = ("update estudiantes set Nombre = ? where CodigoEstudiante = ?");
					ps = con.prepareStatement(sql);
					ps.setString(2, txtCodigoEstudiante.getText());
					ps.setString(1, txtNombre.getText());
					retorno = ps.executeUpdate();
					if(retorno >0 ) {
						JOptionPane.showMessageDialog(null,"Registro Actualizado Correctamente MYSQL" );

					} else {
						JOptionPane.showMessageDialog(null,"No se Pudo Actualizar el Registro" );
					}
						ps.close();
						con.close();
						
						String sqlSQLServer = ("update estudiantes set Nombre = ? where CodigoEstudiante = ?");
						ps = conSQLServer.prepareStatement(sqlSQLServer);
						ps.setString(2, txtCodigoEstudiante.getText());
						ps.setString(1, txtNombre.getText());
						retorno = ps.executeUpdate();
						if(retorno >0 ) {
							JOptionPane.showMessageDialog(null,"Registro Agregado Correctamente en SQLServer" );
							ps.close();
							con.close();
						} else {
							JOptionPane.showMessageDialog(null,"No se Pudo Agregar el Registro en sql server" );
						}
					
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
					}
				
				catch (SQLException e1){
					JOptionPane.showMessageDialog(null,"Error al Acceder a BD");
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(351, 180, 96, 23);
		frame.getContentPane().add(button);
		
	}

}
