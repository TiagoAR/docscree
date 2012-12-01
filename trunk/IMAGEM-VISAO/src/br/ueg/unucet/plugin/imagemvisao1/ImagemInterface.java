package br.ueg.unucet.plugin.imagemvisao1;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.ImageIcon;

import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;

import sun.awt.image.ToolkitImage;
import br.ueg.unucet.quid.extensao.interfaces.IComponenteInterface;
import br.ueg.unucet.quid.extensao.interfaces.IParametro;

/**
 * Interface do ComboBox, contém os componentes que serão exibidos
 * no Descritor de Tela e como setar e obter seu valor.
 * 
 * @author Diego
 *
 */
public class ImagemInterface implements IComponenteInterface {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 344518551310671764L;
	/**
	 * Componente de Preenchimento do ComboBox
	 */
	private Div div;
	private Image imgView;
	private Button btnUpload;
	
	private int imagem_altura;
	private int imagem_largura;
	
	
	/**
	 * Construtor DEFAULT
	 */
	public ImagemInterface() {
		this.div = new Div();
		this.div.setHeight("100%");
		this.div.setWidth("100%");
		this.imgView  = new Image();
		
		this.btnUpload = new Button("Selecionar!");
		this.div.appendChild(imgView);
		this.div.appendChild(this.btnUpload);
		
		/*this.btnUpload.addEventListener("onUpload", new org.zkoss.zk.ui.event.EventListener<org.zkoss.zk.ui.event.UploadEvent>() {

			@Override
			public void onEvent(org.zkoss.zk.ui.event.UploadEvent event) throws Exception {
				
				org.zkoss.util.media.Media media = event.getMedia();
				((Button) event.getTarget()).setLabel(media.getName());
				if (media instanceof org.zkoss.image.Image) {
					org.zkoss.zul.Image image = new org.zkoss.zul.Image();
					imgView.setContent((org.zkoss.image.Image) media);
				
				} else {
					System.out.println("Somente imagem podem ser incluidas");
				}
			}
			
		});*/
		
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int getParametro(Imagem img,String parametro){

		Collection params = img.getListaParametros();
		Iterator<IParametro<String>> iterator = params.iterator();
		while (iterator.hasNext()) {
			IParametro<String> param = iterator.next();
			if (param.getNome().equals(parametro)) {
				if (param.getValor() != null) {
					try{
						return Integer.parseInt(String.valueOf(param.getValor()));
					}catch(Exception e){
						return 10;
					}
				}
			}
		}
		return 10;
	}
	/**
	 * Método que retorna o componente de preenchimento
	 * 
	 * @return Textbox componente
	 */
	public Div getPreenchimento(Imagem img) {
		this.imagem_altura= this.getParametro(img, Imagem.IMAGEM_ALTURA);
		this.imagem_largura = this.getParametro(img, Imagem.IMAGEM_LARGURA);

		this.imgView.setContent( ((ToolkitImage) new ImageIcon(getClass().getResource("/no_image.png")).getImage()).getBufferedImage());
		this.imgView.setHeight(String.valueOf(this.imagem_altura)+"px");
		this.imgView.setWidth(String.valueOf(this.imagem_largura)+"px");
		
		return this.div;
	}
	
	/**
	 * Método que retorna o componente de visualização
	 * 
	 * @return Label componente
	 */
	public Image getVisualizacao(Imagem img) {
		return this.imgView;
	}
	
	@Override
	public void setValor(Object valor) {
		//TODO ver questão do valor
	}
	
	@Override
	public Object getValor() {
		//return this.imgView.;
		return null;
	}

	@Override
	public Object getValor(Object componente) {
		return null;//((Textbox)componente).getText();
	}
}