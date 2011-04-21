package biblioteca
import java.util.Map;
import org.grails.plugins.excelimport.*
import sample.*

class MaterialExcelImporter extends AbstractExcelImporter {

  static Map CONFIG_CUADERNOS_COLUMN_MAP = [
	sheet:'Cuadernos',
	startRow: 2,
	columnMap:  [
			'A':'codigoMateria',
			'B':'materia',
			'C':'catedra',
			'D':'cuatrimestre',
			'E':'autor',
			'F':'tipo'
	]
  ]
  
  static Map CONFIG_RESUMENES_COLUMN_MAP = [
	  sheet:'Resumenes',
	  startRow: 2,
	  columnMap:  [
			'A':'codigoMateria',
			'B':'materia',
			'C':'descripcion',
			'D':'autor',
	]
  ]
  
  static Map CONFIG_APUNTES_COLUMN_MAP = [
	  sheet:'Apuntes',
	  startRow: 1,
	  columnMap:  [
		  	'B':'nombre',
			'C':'serie',
			'D':'autor',
			'E':'tema',
	  ]
  ]

  public MaterialExcelImporter(archivo) {
    super(archivo)
  }

  List<Map> getCuadernos() {
    List bookList = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_CUADERNOS_COLUMN_MAP)
  }

  List<Map> getResumenes() {
	  List bookList = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_RESUMENES_COLUMN_MAP)
	}
  
  List<Map> getApuntes() {
	  List bookList = ExcelImportUtils.convertColumnMapConfigManyRows(workbook, CONFIG_APUNTES_COLUMN_MAP)
	}
}