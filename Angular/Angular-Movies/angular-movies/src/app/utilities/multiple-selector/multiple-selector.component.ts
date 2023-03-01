import { Component, Input } from '@angular/core';
import { multipleSelectorModel } from './multiple-selector.model';

@Component({
  selector: 'app-multiple-selector',
  templateUrl: './multiple-selector.component.html',
  styleUrls: ['./multiple-selector.component.css'],
})
export class MultipleSelectorComponent {
  @Input()
  SelectedItems: multipleSelectorModel[] = [];
  @Input()
  NonSelectedItems: multipleSelectorModel[] = [];
  selectAll() {
    this.SelectedItems.push(...this.NonSelectedItems);
    this.NonSelectedItems = [];
  }
  deSelectAll() {
    this.NonSelectedItems.push(...this.SelectedItems);
    this.SelectedItems = [];
  }
  select(item: multipleSelectorModel, index: number) {
    this.SelectedItems.push(item);
    this.NonSelectedItems.splice(index, 1);
  }
  deSelect(item: multipleSelectorModel, index: number) {
    this.NonSelectedItems.push(item);
    this.SelectedItems.splice(index, 1);
  }
}
