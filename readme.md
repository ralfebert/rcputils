# Potpourri of utility classes for Eclipse RCP development.

* JFace Viewer
	* [TableViewerBuilder](http://www.ralfebert.de/blog/eclipsercp/tableviewerbuilder/) A convenient builder class for creating TableViewers with support for nested properties, sorting and editing

* JFace Data Binding
	* ChangeTracker allows to react on changes in all model or target observables in a DataBindingContext globally. Can be used to set the dirty flag of an editor when something changes.
	* DataBindingEditorPart is a base class for EditorParts that want to use DataBinding. Provides a observable for partname and has a dirty attribute that can be registered as change listener to ChangeTracker. 
	* ModelDataBindingEditorPart is base class for editors that use data binding to edit a single model object.

* Nested properties
	* PropertyValue: Resolves nested property Strings like "company.country.name".
	* PropertyLabelProvider: LabelProvider that gets labels using a bean property.
	* PropertyCellLabelProvider: CellLabelProvider that gets labels using a bean property.
	* PropertyEditingSupport: EditingSupport that gets / sets the values using a bean property.

* Creating random data
	* RandomData: RandomData generates random data. This is helpful for generating test data for prototypes and test cases.

* Helpers
	* DateHelper: Helper methods for dealing with java.util.Date

* Commands / Handler
	* [ToggleHandler](http://www.ralfebert.de/blog/eclipsercp/togglehandler/) Handler base class for style="toggle" command contributions.

	
License:
* Eclipse Public License - v1.0
* http://www.eclipse.org/legal/epl-v10.html