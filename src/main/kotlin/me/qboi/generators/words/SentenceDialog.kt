package me.qboi.generators.words

import org.eclipse.jface.dialogs.Dialog
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.swt.SWT
import org.eclipse.swt.dnd.Clipboard
import org.eclipse.swt.dnd.TextTransfer
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*


class SentenceDialog(parent: Shell, private val sentence: String) : Dialog(parent) {
    private lateinit var sampleButton: Button

    init {
        val layout = GridLayout()
        val dialogArea = getDialogArea()
    }

    override fun createDialogArea(parent: Composite): Control {
        parent.layout = FillLayout().also {
            it.type = SWT.VERTICAL
        }
        return Text(parent, SWT.MULTI or SWT.READ_ONLY or SWT.WRAP).also {
            it.text = sentence
        }
    }

    override fun createButtonsForButtonBar(parent: Composite) {
        // Change parent layout data to fill the whole bar
        parent.layoutData = GridData(SWT.FILL, SWT.CENTER, true, false)
        sampleButton = createButton(parent, IDialogConstants.NO_ID, "Copy", true).also {
            it.addSelectionListener(SelectionCommand {
                Clipboard(parent.display).setContents(arrayOf(sentence), arrayOf(TextTransfer.getInstance()))
            })
        }

        // Create a spacer label
        val spacer = Label(parent, SWT.NONE)
        spacer.layoutData = GridData(SWT.FILL, SWT.CENTER, true, false)

        // Update layout of the parent composite to count the spacer
        val layout = parent.layout as GridLayout
        layout.numColumns++
        layout.makeColumnsEqualWidth = false
        createButton(parent, IDialogConstants.OK_ID, "Close", true)
    }
}
