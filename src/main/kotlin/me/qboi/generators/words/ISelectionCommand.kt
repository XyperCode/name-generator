package me.qboi.generators.words

import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.events.SelectionListener

@FunctionalInterface
interface ISelectionCommand : SelectionListener {
    override fun widgetSelected(e: SelectionEvent)

    override fun widgetDefaultSelected(e: SelectionEvent) {

    }
}