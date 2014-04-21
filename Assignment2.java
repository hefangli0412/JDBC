package assignment2;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import oracle.spatial.geometry.JGeometry;
import oracle.sql.STRUCT;


class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	JLabel LogoLabel;
	JLabel headLabel;
	JButton notifyButton;
	LoginPanel loginPanel;
	SignupPanel signUpPanel;
	SqlPanel sqlPanel;
	ButtonPanel buttonPanel;
	ResultPanel resultPanel;
	Connection conn=null;
	ArrayList<String> requester = new ArrayList<String>();
	ArrayList<String> Relation= new ArrayList<String>();
	int countrequest=0;
	JTextArea resultArea = null;
	JScrollPane scrollPane = null;
	int trigger = 0;
	JLabel showLabel;
	int hasRequest = 0;
	StringBuffer SQLOut = new StringBuffer();
	ArrayList<Term> productList;
	int totalPrice;

	MainFrame(){
		setResizable(false);
		setLayout(null);
		setSize(1100, 700);
		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		setLocation((width-1100)/2,(height-700)/2);
		setTitle("This is GUI for database homework");
		SetLogo();
		setLoginPanel();
		setSignupPanel();
		setSqlPanel();
		setButtonPanel();
		setResultPanel();
		buttonPanel.disablePanel();
	}

	public void disableResult(){
    	resultArea.setText("");
    	resultArea.setEditable(false);
    	resultArea.setEnabled(false);
    	scrollPane.setEnabled(false);
	}

	public void setResultPanel(){
		resultArea = new JTextArea(10,30);
		resultArea.setLineWrap(true);
		scrollPane = new JScrollPane(resultArea);
		headLabel = new JLabel("Shopping System");
		add(scrollPane);
		add(headLabel);
		
		headLabel.setFont(new Font("Serif", Font.BOLD, 30));
		headLabel.setBounds(240,20 , 360, 60);
		scrollPane.setBounds(20, 100,740, 250);
	}
	
	public void setResultOutput(StringBuffer sb) {
		resultArea.setText(sb.toString());
		resultArea.setEnabled(true);
	}

	public void SetLogo(){
		Image image;
		try {
			image = ImageIO.read(new File("usc_viterbi_logo.jpg"));
			ImageIcon icon = new ImageIcon(image);
			LogoLabel = new JLabel();
			LogoLabel.setIcon(icon);
			LogoLabel.setBounds(830, 500, 300, 150);

			add(LogoLabel);
		} catch (IOException e) {
			e.printStackTrace();
		} // this generates an image file
	}
	
	public void setButtonPanel(){
		buttonPanel = new ButtonPanel();
		buttonPanel.setBounds(30, 380, 700, 90);
		this.add(buttonPanel);

		
		buttonPanel.buttons[0].addActionListener(new ActionListener() {
	          
            public void actionPerformed(ActionEvent e) {
            	StringBuffer result= new StringBuffer();
            	/* Fill this function
				 * Press this your account button, you should be able to list
				 * current log in customer information in the result panel
				 * (Including Email, First Name, Last Name, Address)
				 * You can define the output format 
				 */
            	String sql;            	
            	Statement stmt;
        		ResultSet re;
        		
        		conn = ConnectDB.openConnection(); // open connection
        		try {
        			stmt = conn.createStatement();
        			sql = "select * from CUSTOMERS where email = '"
        					+ loginPanel.getUserName() +"'";
					SQLOut.append(sql + "\n\n");
					setSQLOutput(SQLOut);
					re = stmt.executeQuery(sql);
					
					if (re.next()) {
						String email = re.getString("EMAIL");
						String fname = re.getString("FNAME");
						String lname = re.getString("LNAME");
						String addrid = re.getString("ADDR_ID");
						result.append("email: " + email + "\nfirst name: "
								+ fname + "\nlast name: " + lname
								+ "\naddress: " + addrid);
						setResultOutput(result);
					}										
					ConnectDB.closeConnection(conn);
					
				} catch (SQLException e1) {
					e1.printStackTrace();
					ConnectDB.closeConnection(conn);
					return;
				}
            }
        });
		
		buttonPanel.buttons[1].addActionListener(new ActionListener() {
          
            public void actionPerformed(ActionEvent e) {
            	StringBuffer result= new StringBuffer();
				/* Fill this function
				 * Press this all products button, you should be able to list
				 * all the products which are visible to you
				 * You can define the output format 
				 */
            	String sql;            	
            	Statement stmt;
        		ResultSet re;
        		
        		conn = ConnectDB.openConnection(); // open connection
        		try {
        			stmt = conn.createStatement();
        			sql = "select * from PRODUCTS";
					SQLOut.append(sql + "\n\n");
					setSQLOutput(SQLOut);
					re = stmt.executeQuery(sql);
					
					while (re.next()) {
						String prodid = re.getString("PROD_ID");
						String category = re.getString("CATEGORY");
						String brand = re.getString("BRAND");
						String name = re.getString("NAME");
						String price = re.getString("PRICE");
						result.append("product ID: " + prodid + "\ncategory: " + category
								+ "\nbrand: " + brand + "\nname: " + name
								+ "\nprice: $" + price + "\n\n");
						setResultOutput(result);
					}										
					ConnectDB.closeConnection(conn);
					
				} catch (SQLException e1) {
					e1.printStackTrace();
					ConnectDB.closeConnection(conn);
					return;
				}
            }
        });

		buttonPanel.buttons[2].addActionListener(new ActionListener() {
          
            public void actionPerformed(ActionEvent e) {
            	final Frame0 frame=new Frame0();
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setVisible(true);
                frame.btn1.addActionListener(new ActionListener() {
                 
                    public void actionPerformed(ActionEvent e) {
                    	StringBuffer result= new StringBuffer();
						/* Fill this function
						 * Press this choose category Button, after choosing and
						 * pressing OK
						 * you should be able to list all products belong to
						 * this category
						 */
                    	String sql;            	
                    	Statement stmt;
                		ResultSet re;
                		
                    	conn = ConnectDB.openConnection(); // open connection
                		try {
                			stmt = conn.createStatement();
                			sql = "select * from PRODUCTS where CATEGORY = '"
                					+ frame.combo.getSelectedItem() +"'";
        					SQLOut.append(sql + "\n\n");
        					setSQLOutput(SQLOut);
        					re = stmt.executeQuery(sql);
        					
        					while (re.next()) {
        						String prodid = re.getString("PROD_ID");
        						String name = re.getString("NAME");
        						String brand = re.getString("BRAND");
        						String price = re.getString("PRICE");
								result.append("product ID: " + prodid
										+ "   name: " + name + "   brand: "
										+ brand + "   price: $" + price + "\n\n");
        						setResultOutput(result);
        					}									
        					ConnectDB.closeConnection(conn);
        					
        				} catch (SQLException e1) {
        					e1.printStackTrace();
        					ConnectDB.closeConnection(conn);
        					return;
        				}
                    }
                });
            }
        });

		
		buttonPanel.buttons[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	        	final Frame1 frame=new Frame1("Please input Price Range ");
	            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	            frame.setVisible(true);

	            frame.btn1.addActionListener(new ActionListener() {
	               
	                public void actionPerformed(ActionEvent e) {
	                	StringBuffer result= new StringBuffer();
						/* Fill this function
						 * Press this set price range Button, you should be able
						 * to set price range
						 * Pressing "Set Price Range" button, a new window will
						 * pop out.
						 * Then you can enter "Min_Price" & "Max_Price" and
						 * press "Search" button,
						 * and then all products belong to category that you
						 * choose should be shown in the result panel.
						 */
	                	String sql;            	
                    	Statement stmt;
                		ResultSet re;
                		
                    	conn = ConnectDB.openConnection(); // open connection
                		try {
                			stmt = conn.createStatement();
                			sql = "select * from PRODUCTS where price >= " +
                					Integer.parseInt(frame.txtfield[0].getText()) +
                					" and price <= " +
                					Integer.parseInt(frame.txtfield[1].getText());
        					SQLOut.append(sql + "\n\n");
        					setSQLOutput(SQLOut);
        					re = stmt.executeQuery(sql);
        					
        					while (re.next()) {
        						String prodid = re.getString("PROD_ID");
        						String category = re.getString("CATEGORY");
        						String name = re.getString("NAME");
        						String brand = re.getString("BRAND");
        						String price = re.getString("PRICE");
								result.append("product ID: " + prodid
										+ "   category: " + category + "name: "
										+ name + "   brand: " + brand
										+ "   price: $" + price + "\n\n");
        						setResultOutput(result);
        					}										
        					ConnectDB.closeConnection(conn);

						} catch (SQLException e1) {
							e1.printStackTrace();
							ConnectDB.closeConnection(conn);
							return;
						} catch (NumberFormatException e2) {
							JOptionPane.showMessageDialog(null, "Please input number(s)", 
									"Warning", JOptionPane.WARNING_MESSAGE);
							ConnectDB.closeConnection(conn);
							return;
						}
					}
				});
			}
		});
		
		buttonPanel.buttons[4].addActionListener(new ActionListener() {
           
			public void actionPerformed(ActionEvent e) {
				final Frame2 frame = new Frame2();
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
				
				productList = new ArrayList<Term>();
				totalPrice = 0;

				frame.btn1.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						/* Fill this function 
						 * You can enter "Product ID" and "Quantity", 
						 * and then you press continue, the Total
						 * Price should be shown correctly.
						 */
						String sql;
						Statement stmt;
						ResultSet re;

						conn = ConnectDB.openConnection(); // open connection
						try {
							String prodid = frame.productID.getText();
							int quantity = Integer.parseInt(frame.quantity.getText());
							int price = 0;
							
							stmt = conn.createStatement();
                			sql = "select * from SELLER_PROVIDES_PRODUCTS where PROD_ID = '"
                					+ prodid +"' and QUANTITY >= " + quantity;
        					SQLOut.append(sql + "\n\n");
        					setSQLOutput(SQLOut);
        					re = stmt.executeQuery(sql);
        					
        					if (re.next()) {
        						productList.add(new Term(prodid, quantity));
        						sql = "select PRICE from PRODUCTS where PROD_ID = '"
        								+ prodid + "'";
        						re = stmt.executeQuery(sql);
            					SQLOut.append(sql + "\n\n");
            					setSQLOutput(SQLOut);
            					if (re.next()) {
            						price = Integer.parseInt(re.getString("PRICE"));
            					}
            					totalPrice += price * quantity;
            					frame.totalPrice.setText(totalPrice + "");
        					} else {
        						JOptionPane.showMessageDialog(null, "Cannot add item(s)", 
    									"Warning", JOptionPane.WARNING_MESSAGE);
        					}        					
							ConnectDB.closeConnection(conn);

						} catch (SQLException e1) {
							e1.printStackTrace();
							ConnectDB.closeConnection(conn);
							return;
						} catch (NumberFormatException e2) {
							JOptionPane.showMessageDialog(null, "Quantity should be a number", 
									"Warning", JOptionPane.WARNING_MESSAGE);
							ConnectDB.closeConnection(conn);
							return;
						}
					}
				});

				frame.btn2.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						/*
						 * Fill this function 
						 * Press "Order Products" button, a new window will pop out. 
						 * Then you can enter "Product ID" and "Quantity", and then you press
						 * continue, the Total Price should be shown correctly.
						 * Then, you can press "Place Order" to complete this
						 * order. This new order should be synchronized in the
						 * database.
						 */
						frame.dispose();
						
						String sql;            	
                    	Statement stmt;
                		ResultSet re;
                		
                    	conn = ConnectDB.openConnection(); // open connection
                		try {
                			stmt = conn.createStatement();
                			
    						/* generate orderId */
    						sql = "select ORDER_ID from ORDERS";
    						re = stmt.executeQuery(sql);
    						int num_max = 0;
    						while (re.next()) {							
    							String oNum = re.getString("ORDER_ID").substring(1);
    							int num = Integer.parseInt(oNum);
    							num_max = num > num_max ? num : num_max;
    						}
    						
    						System.out.println(num_max);
    						ListIterator<Term> iter1 = productList.listIterator();
        					while (iter1.hasNext()) {
        						Term aTerm = iter1.next();
        						System.out.println(aTerm.prodid + "  " + aTerm.quantity);
        					}
    						
                			sql = "insert into ORDERS (ORDER_ID, EMAIL, TOTAL_PRICE) values ('O"
								+ (num_max+1) + "','" + loginPanel.getUserName() + "'," + totalPrice + ")";
        					SQLOut.append(sql + "\n\n");
        					setSQLOutput(SQLOut);
        					re = stmt.executeQuery(sql);
        					
        					ListIterator<Term> iter = productList.listIterator();
        					while (iter.hasNext()) {
        						Term aTerm = iter.next();
        						String p = aTerm.prodid;
        						int q = aTerm.quantity;
        						sql = "insert into ORDER_CONTAINS_PRODUCTS (ORDER_ID, PROD_ID, QUANTITY) values ('O"
        								+ (num_max+1) + "','" + p + "'," + q + ")";            					
            					SQLOut.append(sql + "\n\n");
            					setSQLOutput(SQLOut);
            					re = stmt.executeQuery(sql);
            					
            					sql = "update SELLER_PROVIDES_PRODUCTS set QUANTITY=QUANTITY-" + q
            							+ " where PROD_ID = '" + p + "'";            					
            					SQLOut.append(sql + "\n\n");
            					setSQLOutput(SQLOut);
            					re = stmt.executeQuery(sql);
        					}        				        					        				            					
        					ConnectDB.closeConnection(conn);
        					
        				} catch (SQLException e1) {
        					e1.printStackTrace();
        					ConnectDB.closeConnection(conn);
        					return;
        				}
					}
				});
				
			}
		});

		buttonPanel.buttons[5].addActionListener(new ActionListener() {
	          
            public void actionPerformed(ActionEvent e) {
            	StringBuffer result= new StringBuffer();
				/* Fill this function 
				 * Press "Your Orders", all order history of this customer
				 * should be shown in the result panel.
				 */
            	String sql;            	
            	Statement stmt;
        		ResultSet re;
        		
        		conn = ConnectDB.openConnection(); // open connection
        		try {
        			stmt = conn.createStatement();
        			sql = "select * from ORDERS where email = '"
        					+ loginPanel.getUserName() +"'";
					SQLOut.append(sql + "\n\n");
					setSQLOutput(SQLOut);
					re = stmt.executeQuery(sql);
					
					while (re.next()) {
						String orderid = re.getString("ORDER_ID");
						String totalPrice = re.getString("TOTAL_PRICE");
						String placeTime = re.getString("PLACE_TIME");
						String shippedTime = re.getString("SHIPPED_TIME");
						String estiArriTime = re.getString("ESTI_ARRI_TIME");
						String signedTime = re.getString("SIGNED_TIME");
						String trackingNum = re.getString("TRACKING_NUM");
						String addrid = re.getString("ADDR_ID");
						result.append("orderID: " + orderid + "\ntotal price: "
								+ totalPrice + "\nplace time: " + placeTime
								+ "\nshipped time: " + shippedTime
								+ "\nestimated arrival time: " + estiArriTime
								+ "\nsigneded time: " + signedTime
								+ "\ntracking number: " + trackingNum
								+ "\naddressID: " + addrid + "\n\n");
						setResultOutput(result);
					}										
					ConnectDB.closeConnection(conn);
					
				} catch (SQLException e1) {
					e1.printStackTrace();
					ConnectDB.closeConnection(conn);
					return;
				}
            }
        });

		
		buttonPanel.buttons[6].addActionListener(new ActionListener() {
	           
	        public void actionPerformed(ActionEvent e) {
	        	final Frame5 frame=new Frame5("Product ID : ","Review : ");
	            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	            frame.setVisible(true);

	            frame.btn1.addActionListener(new ActionListener() {
	               
	                public void actionPerformed(ActionEvent e) {
						/* Fill this function Press "Review Products" button, a
						 * new window will pop out. Input product ID and review
						 * content and press the OK button, this information
						 * should be inserted into database.
						 */
	    				String sql;
	    				Statement stmt;
	    				ResultSet re;
	    				String err = "";		    				
	    				String prodid = frame.txtfield.getText();
	                	String content = frame.textArea.getText();
	                	
						conn = ConnectDB.openConnection(); // open connection
						try {
							stmt = conn.createStatement();	
							boolean isValid = true;
    						/* verify productID */
							sql = "select * from PRODUCTS where PROD_ID = '"
									+ prodid + "'";
							SQLOut.append(sql + "\n");
							setSQLOutput(SQLOut);
							re = stmt.executeQuery(sql);
							if (!re.next()) {
								err += "wrong productID\n";								
								isValid = false;
							}
							/* verify content */
							if (content.isEmpty() || content.length() > 200) {
								err += "wrong content length\n";
								isValid = false;
							}
							
							if (isValid) {
								/* generate reviewId */
	    						sql = "select REVW_ID from REVIEWS";
	    						re = stmt.executeQuery(sql);
	    						int num_max = 0;
	    						while (re.next()) {							
	    							String rNum = re.getString("REVW_ID").substring(1);
	    							int num = Integer.parseInt(rNum);
	    							num_max = num > num_max ? num : num_max;
	    						}
	    						
	    						/* insert into REVIEWS */
	    						sql = "insert into REVIEWS (REVW_ID,PROD_ID,EMAIL,CONTENT) values ('R"
	    								+ (num_max+1) + "','" + prodid + "','" 
	    								+ loginPanel.getUserName() + "','" + content + "')";
	    						SQLOut.append(sql + "\n");
								setSQLOutput(SQLOut);
								re = stmt.executeQuery(sql);
							} else {
								JOptionPane.showMessageDialog(null, "Failed!\n" + err);
							}
							ConnectDB.closeConnection(conn);

						} catch (SQLException e1) {
							e1.printStackTrace();
							ConnectDB.closeConnection(conn);
							return;
						}
					}
				});
			}
		});
		
		buttonPanel.buttons[7].addActionListener(new ActionListener() {
	           
	        public void actionPerformed(ActionEvent e) {
	        	final Frame4 frame=new Frame4("Product ID : ","Submit");
	            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	            frame.setVisible(true);

	            frame.btn1.addActionListener(new ActionListener() {
	               
	                public void actionPerformed(ActionEvent e) {
	                	StringBuffer result= new StringBuffer();
						/* Fill this function 
						 * Press "List All Reviews" button, a new window will pop out.				
						 * Input "Product ID" and press submit, all reviews
						 * about this product should be shown in the result panel.
						 */
	                	String sql;            	
	                	Statement stmt;
	            		ResultSet re;
	            		
						conn = ConnectDB.openConnection(); // open connection
						try {
							stmt = conn.createStatement();
							sql = "select * from REVIEWS where PROD_ID = '"
									+ frame.txtfield.getText() + "'";
							SQLOut.append(sql + "\n\n");
							setSQLOutput(SQLOut);
							re = stmt.executeQuery(sql);

							while (re.next()) {
								String revwid = re.getString("REVW_ID");
								String email = re.getString("EMAIL");
								String postedTime = re.getString("POSTED_TIME");
								String rating = re.getString("RATING");
								String content = re.getString("CONTENT");
								result.append("reviewID: " + revwid
										+ "\nauthor: " + email
										+ "\nposted time: " + postedTime
										+ "\nrating: " + rating + "\ncontent: "
										+ content + "\n\n");
								setResultOutput(result);
							}
							ConnectDB.closeConnection(conn);

						} catch (SQLException e1) {
							e1.printStackTrace();
							ConnectDB.closeConnection(conn);
							return;
						}
					}
				});
			}
		});
		
		buttonPanel.buttons[8].addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				final Frame4 frame = new Frame4("Review ID : ", "Like it");
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);

				frame.btn1.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						/* Fill this function 
						 * Press "Like Reviews" button, a new
						 * window will pop out. Input "Review ID" and press
						 * "Like it", this information should be inserted into
						 * database.
						 */
						String sql;
	    				Statement stmt;
	    				ResultSet re;		    				
	    				String revwid = frame.txtfield.getText();
	                	
						conn = ConnectDB.openConnection(); // open connection
						try {
							stmt = conn.createStatement();	
    						/* verify reviewID */
							sql = "select * from REVIEWS where REVW_ID = '"
									+ revwid + "'";
							SQLOut.append(sql + "\n");
							setSQLOutput(SQLOut);
							re = stmt.executeQuery(sql);
							if (!re.next()) {
								JOptionPane.showMessageDialog(null, "Failed!\nwrong reviewID");
								ConnectDB.closeConnection(conn);
								return;
							}
								    						
    						/* insert into REVIEWS */
    						sql = "insert into CUSTOMER_LIKES_REVIEW (REVW_ID,EMAIL) values ('"
    								+ revwid + "','" + loginPanel.getUserName() + "')";
    						SQLOut.append(sql + "\n");
							setSQLOutput(SQLOut);
							re = stmt.executeQuery(sql);							
							ConnectDB.closeConnection(conn);

						} catch (SQLException e1) {
							e1.printStackTrace();
							ConnectDB.closeConnection(conn);
							return;
						}												
					}
				});
			}
		});
		
		buttonPanel.buttons[9].addActionListener(new ActionListener() {
	          
            public void actionPerformed(ActionEvent e) {
            	StringBuffer result= new StringBuffer();
				/* Fill this function 
				 * Press "List All Likes" button, all reviews that liked by this
				 * customer should be shown in the result panel.
				 */
            	String sql;            	
            	Statement stmt;
        		ResultSet re;
        		
        		conn = ConnectDB.openConnection(); // open connection
        		try {
        			stmt = conn.createStatement();
        			sql = "select * from CUSTOMER_LIKES_REVIEW where email = '"
        					+ loginPanel.getUserName() +"'";					
					SQLOut.append(sql + "\n\n");
					setSQLOutput(SQLOut);
					re = stmt.executeQuery(sql);
					
					while (re.next()) {
						String revwid = re.getString("REVW_ID");
						result.append("reviewID: " + revwid + "\n");
						setResultOutput(result);
					}										
					ConnectDB.closeConnection(conn);
					
				} catch (SQLException e1) {
					e1.printStackTrace();
					ConnectDB.closeConnection(conn);
					return;
				}
            }
        });
		
		buttonPanel.buttons[10].addActionListener(new ActionListener() {
	          
            public void actionPerformed(ActionEvent e) {
            	StringBuffer result= new StringBuffer();
				/* Fill this function 
				 * Press "Nearest Seller" button, the nearest seller info for
				 * this customer should be shown in the result panel.
				 * This is a spatial query 
				 */
            	String sql;            	
            	Statement stmt;
        		ResultSet re;
        		
        		conn = ConnectDB.openConnection(); // open connection
        		try {
        			stmt = conn.createStatement();        			
        			sql = "select EMAIL "
        					+ "from (select a2.ADDR_ID, a2.LOCATION, s.EMAIL from ADDRESSES a2, SELLERS s "
        					+ "where a2.ADDR_ID = s.ADDR_ID and not s.email = '" + loginPanel.getUserName() +"') t "
        					+ "where SDO_NN(t.LOCATION, "
							+ "(select LOCATION from ADDRESSES a1, CUSTOMERS c "
        					+ "WHERE a1.ADDR_ID = c.ADDR_ID and c.EMAIL='" + loginPanel.getUserName() +"')," 
							+ "'sdo_batch_size=1') = 'TRUE' ";
        			SQLOut.append(sql + "\n\n");
					setSQLOutput(SQLOut);
					re = stmt.executeQuery(sql);
					if (re.next()) {
						result.append(re.getString("EMAIL"));
						setResultOutput(result);
					}					
        			
					ConnectDB.closeConnection(conn);
					
				} catch (SQLException e1) {
					e1.printStackTrace();
					ConnectDB.closeConnection(conn);
					return;
				}            	
            }
        });
		
		buttonPanel.buttons[11].addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
            	final Frame3 frame=new Frame3("Please input coordinate: ");
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setVisible(true);

                frame.btn1.addActionListener(new ActionListener() {
                  
                    public void actionPerformed(ActionEvent e) {
                    	StringBuffer result= new StringBuffer();
						/* Fill this function 
						 * Press this Button, input left top corner coordinate
						 * and right down corner coordinate
						 * press ok, you should be able list the
						 * information(including address information) about
						 * seller who lives in this area. Close query window
						 * This is a spatial query */
                    	String sql;            	
                    	Statement stmt;
                		ResultSet re; 
                		String tx = frame.txtfield[0].getText();
                    	String ty = frame.txtfield[1].getText();
                    	String bx = frame.txtfield[2].getText();
                    	String by = frame.txtfield[3].getText();
                		
                		conn = ConnectDB.openConnection(); // open connection
                		try {                			
                			stmt = conn.createStatement();
                			sql = "select EMAIL from (select a.ADDR_ID, a.LOCATION, s.EMAIL from ADDRESSES a, SELLERS s where a.ADDR_ID = s.ADDR_ID) t "
                					+ "where SDO_INSIDE(t.LOCATION, SDO_GEOMETRY(2003, null, null, SDO_ELEM_INFO_ARRAY(1,1003,3),"
        							+ "SDO_ORDINATE_ARRAY(" + Double.parseDouble(tx) +","+ Double.parseDouble(by) +"," 
                					+ Double.parseDouble(bx) + "," + Double.parseDouble(ty) + "))) = 'TRUE' " + "order by EMAIL";                			
                			SQLOut.append(sql + "\n\n");
        					setSQLOutput(SQLOut);
        					re = stmt.executeQuery(sql);        					
        					
        					while (re.next()) {
        						result.append(re.getString("EMAIL") + "\n");        						
        					}			
        					setResultOutput(result);
        					ConnectDB.closeConnection(conn);
        					
        				} catch (SQLException e1) {
        					e1.printStackTrace();
        					ConnectDB.closeConnection(conn);
        					return;
        				} catch (NumberFormatException e2) {
							JOptionPane.showMessageDialog(null, "Please input number(s)", 
									"Warning", JOptionPane.WARNING_MESSAGE);
							ConnectDB.closeConnection(conn);
							return;
						}          	                    	
                    }
                });
            }
        });		
	}
	
	public void setSQLOutput(StringBuffer sb)
	{
		sqlPanel.SQLArea.setText(sb.toString());
		sqlPanel.SQLArea.setEnabled(true);
	}
	public void setSqlPanel(){
		sqlPanel = new SqlPanel();
		showLabel = new JLabel("The corresponding SQL sentence:");
		showLabel.setBounds(30, 490, 400, 20);
		sqlPanel.setBounds(5, 515,790, 150);
		this.add(sqlPanel);
		this.add(showLabel);
	}

	public void setLoginPanel(){
		
		loginPanel = new LoginPanel();
		this.add(loginPanel);

		loginPanel.signup.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) { // signup button
				signUpPanel.enablePanel();
			}
		});
		
        loginPanel.login.addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) { // login button
				// buttonPanel.enablePanel();
				/* Fill this function 
				 * Press this Button, you should be able match the user information. 
				 * If valid, keep the user email information(but
				 * can't modified) and clear the password
				 * If invalid, you should pop up a dialog box to notify user,
				 * then enable signup panel for user to register.
				 * After logged in, you should change this button's function as
				 * logout which means disable all the panel, return to the
				 * original state
				 */
				if (trigger == 0) { // logout state
					// match account
					conn = ConnectDB.openConnection(); // connect database
					String QueryStr = "select EMAIL from CUSTOMERS where EMAIL = '"
							+ loginPanel.username.getText()
							+ "' and PASSWD = '"
							+ loginPanel.password.getText() + "'";
					SQLOut.append(QueryStr + "\n\n");
					try {
						Statement stmt = conn.createStatement(); 
						ResultSet re = stmt.executeQuery(QueryStr); // verify login info
						if (re.next()) { // valid login
							loginPanel.setUserName(loginPanel.username
									.getText().toString()); // set Username
							loginPanel.disablePanel(); // disable login panel
							loginPanel.password.setText(""); // clear passwd
							trigger = 1; // set login state
							loginPanel.login.setText("logout");
							signUpPanel.disablePanel(); // disable signup panel
							buttonPanel.enablePanel(); // enable operation panel
							loginPanel.signup.setEnabled(false);
							
						} else { // invalid login
							JOptionPane.showMessageDialog(null,
									"No ... please signup");
							signUpPanel.enablePanel(); // enable signup panel
							// loginPanel.disablePanel();
						}
						ConnectDB.closeConnection(conn); // disconnect database

					} catch (SQLException e1) {
						e1.printStackTrace();
						ConnectDB.closeConnection(conn);
						return;
					}
					
				} else { // login state
					loginPanel.login.setText("login");
					loginPanel.enablePanel(); // enable login panel
					loginPanel.signup.setEnabled(true);
					loginPanel.password.setText(""); // clear passwd
					loginPanel.username.setText(""); // clear username
					disableResult();
					trigger = 0;
					buttonPanel.disablePanel();
				}
				setSQLOutput(SQLOut);
			}
		});
	}

	public void setSignupPanel() {

		signUpPanel = new SignupPanel();
		this.add(signUpPanel);
		signUpPanel.signup.addActionListener(new ActionListener() { // signup button

			public void actionPerformed(ActionEvent e) {
				/* Fill this function
				 * Press this signup button, you should be able check whether
				 * current account is existed. If existed, pop up an error, if
				 * not check input validation (You can design this part according
				 * to your database table's restriction) create the new account
				 * information
				 * pop up a standard dialog box to show <succeed or failed> 
				 */
				String sql;
				Statement stmt;
				ResultSet re;
				String err = "";								
				
            	String email = signUpPanel.email.getText();
            	String password = signUpPanel.password.getText();
				String password2 = signUpPanel.password2.getText();
            	String fname = signUpPanel.fname.getText();
            	String lname = signUpPanel.lname.getText();            	
            	String birthday = signUpPanel.birthday.getText();
            	String str_no = signUpPanel.str_no.getText();
            	String str_address = signUpPanel.str_address.getText();
            	String city = signUpPanel.city.getText();
            	String state = signUpPanel.state.getText();
            	String zip = signUpPanel.zip.getText();
            	
            	conn = ConnectDB.openConnection(); // open connection
            	try {
					sql = "select * from CUSTOMERS where EMAIL = '" + email + "'";
					SQLOut.append(sql + "\n");				
					stmt = conn.createStatement();
					re = stmt.executeQuery(sql);
					if (re.next()) {	
						err = "Failed!\nUser Exits" ;
						JOptionPane.showMessageDialog(null, err);
						ConnectDB.closeConnection(conn);
						return;
					} 
					
					boolean isValid = true;
					if (email.isEmpty() || email.length() > 20 || !isValidEmail(email)) {
						isValid = false;
						err += "email ";
					}
					if (password.length() == 0 || password.length() > 20 || !password2.equals(password)) {
						isValid = false;
						err += "password ";
					}
					if (fname.isEmpty() || fname.length() > 20) {
						isValid = false;
						err += "firstname ";
					}
					if (lname.isEmpty() || lname.length() > 20) {
						isValid = false;
						err += "lastname ";
					}
					if (birthday.isEmpty() || birthday.length() > 20) {
						isValid = false;
						err += "birthday ";
					}
					if (str_no.isEmpty() || str_no.length() > 20 || !isInteger(str_no)) {
						isValid = false;
						err += "strNo ";
					}
					if (str_address.isEmpty() || str_address.length() > 50) {
						isValid = false;
						err += "strAddress ";
					}
					if (city.isEmpty() || city.length() > 20) {
						isValid = false;
						err += "city ";
					}
					if (state.isEmpty() || state.length() > 2) {
						isValid = false;
						err += "state ";
					}
					if (zip.isEmpty() || zip.length() > 20 || !isInteger(zip)) {
						isValid = false;
						err += "zip";
					}									

					if (!isValid) {
						err = "Failed!\nillegal field(s): " + err;
						JOptionPane.showMessageDialog(null, err);
					} else {
						/* generate addrId */
						sql = "select ADDR_ID from ADDRESSES";
						re = stmt.executeQuery(sql);
						int num_max = 0;
						while (re.next()) {							
							String addrNum = re.getString("ADDR_ID").substring(1);
							int num = Integer.parseInt(addrNum);
							num_max = num > num_max ? num : num_max;
						}

						sql = "insert into ADDRESSES (ADDR_ID, STR_NO, STREET, CITY, STATE, ZIP) values ('A"
								+ (num_max+1) + "','" + str_no + "','" + str_address + "','" + city + "','" + state 
								+ "','" + zip + "')";
						SQLOut.append(sql + "\n\n");
						setSQLOutput(SQLOut);
						re = stmt.executeQuery(sql);						
						
						sql = "insert into CUSTOMERS (EMAIL, FNAME, LNAME, BIRTHDATE, ADDR_ID, PASSWD) values ('"
								+ email + "','" + fname + "','" + lname + "','" + birthday + "','A" + (num_max+1)
								+ "','" + password.toString() + "')";
						SQLOut.append(sql + "\n\n");
						setSQLOutput(SQLOut);
						re = stmt.executeQuery(sql);						
												
						JOptionPane.showMessageDialog(null, "Succeeded");
					}				
					ConnectDB.closeConnection(conn);
					
				} catch (SQLException e1) {
					e1.printStackTrace();
					ConnectDB.closeConnection(conn);
					return;
				}             	
			}		

			private boolean isValidEmail(String email) {
				EmailValidator ev = new EmailValidator();
				return ev.validate(email);
			}
			
			public boolean isInteger(String value) {
				try {
					Integer.parseInt(value);
					return true;
				} catch (NumberFormatException e) {
					return false;
				}
			}
		});
		
		signUpPanel.disablePanel();
	}

	class Term {
		String prodid;
		int quantity;

		Term(String prodid, int quantity) {
			this.prodid = prodid;
			this.quantity = quantity;
		}
	}

	class EmailValidator {
		private Pattern pattern;
		private Matcher matcher;
		private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		public EmailValidator() {
			pattern = Pattern.compile(EMAIL_PATTERN);
		}

		/* Validate hex with regular expression */		 
		public boolean validate(final String hex) {
			matcher = pattern.matcher(hex);
			return matcher.matches();
		}
	}
}

class ConnectDB{

	public static Connection openConnection(){
        try{
        	// register JDBC driver
	        String driverName = "oracle.jdbc.driver.OracleDriver";
	        Class.forName(driverName); // runtime reflection

	        // set the username and password for your connection.
	        String url = "jdbc:oracle:thin:@localhost:1521:hefangli";
	        String uname = "hefangli";
	        String pwd = "123";

	        // open a connection
	        return DriverManager.getConnection(url, uname, pwd);
        }
        catch(ClassNotFoundException e){
        	System.out.println("Class Not Found");
        	e.printStackTrace();
        	return null;
        }
        catch(SQLException sqle){
        	System.out.println("Connection Failed");
        	sqle.printStackTrace();
        	return null;
        }
	}
	
	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("connection closing error");
		}
	}
}


public class Assignment2 {

	public static void main(String[] args) {
    	MainFrame frame = new MainFrame();
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


