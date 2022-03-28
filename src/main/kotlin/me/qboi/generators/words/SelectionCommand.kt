package me.qboi.generators.words

import org.eclipse.swt.events.SelectionEvent

class SelectionCommand(private val command: (SelectionEvent) -> Unit) : ISelectionCommand {
    override fun widgetSelected(e: SelectionEvent) {
        command(e)
    }
}