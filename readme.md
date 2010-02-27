# Utility classes for Eclipse RCP development

## p2 repository

	http://www.ralfebert.de/projects/releases/rcputils/

## Overview

* JFace Structured Viewers
	* [TableViewerBuilder](http://www.ralfebert.de/blog/eclipsercp/tableviewerbuilder/) A convenient builder class for creating TableViewers with support for nested properties, sorting and editing
	* [ContextMenu](http://www.ralfebert.de/blog/eclipsercp/commands_context_menu/) is an utility class to create context menus for structured viewers.
	* [SelectionUtils.getIterable()](http://www.ralfebert.de/blog/eclipsercp/selection_iterable/): Iterate over a JFace selection in a for each loop

* Concurrent UIs
	* `UIProcess` is a Job base class which allows to update the UI after the job was completed.

* JFace Data Binding
	* ChangeTracker allows to react on changes in all model or target observables in a DataBindingContext globally. Can be used to set the dirty flag of an editor when something changes.
	* DataBindingEditorPart is a base class for EditorParts that want to use DataBinding. Provides a observable for partname and has a dirty attribute that can be registered as change listener to ChangeTracker. 
	* ModelDataBindingEditorPart is base class for editors that use data binding to edit a single model object.

* Nested properties
	* PropertyValue: Resolves nested property Strings like "company.country.name".
	* PropertyLabelProvider: LabelProvider that gets labels using a bean property.
	* PropertyCellLabelProvider: CellLabelProvider that gets labels using a bean property.
	* PropertyEditingSupport: EditingSupport that gets / sets the values using a bean property.

* Mockups
	* RandomData: RandomData generates random data. This is helpful for generating test data for UI mockup prototypes and test cases.

* Commands / Handler
	* [ToggleHandler](http://www.ralfebert.de/blog/eclipsercp/togglehandler/) Handler base class for style="toggle" command contributions.

	
## License

* Eclipse Public License - v1.0
* http://www.eclipse.org/legal/epl-v10.html
