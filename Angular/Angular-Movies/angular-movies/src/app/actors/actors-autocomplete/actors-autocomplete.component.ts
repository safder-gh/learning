import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import {
  MatAutocompleteModule,
  MatAutocompleteSelectedEvent,
} from '@angular/material/autocomplete';
import { MatTable } from '@angular/material/table';
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';

@Component({
  selector: 'app-actors-autocomplete',
  templateUrl: './actors-autocomplete.component.html',
  styleUrls: ['./actors-autocomplete.component.css'],
})
export class ActorsAutocompleteComponent implements OnInit {
  ngOnInit(): void {
    this.control.valueChanges.subscribe((value) => {
      this.actors = this.originalActors;
      this.actors = this.actors.filter(
        (actor) => actor.name.indexOf(value) !== -1
      );
    });
  }
  actors = [
    {
      name: 'Qazi Wajid',
      picture: 'assets/qazi_wajid.jpg',
    },
    { name: 'Ismail Tara', picture: 'assets/ismail_tara.jpg' },
    { name: 'Suhail Asghar', picture: 'assets/suhail_asghar.jpg' },
  ];
  originalActors = this.actors;
  selectedActors: any = [];
  columnsToDisplay = ['picture', 'name', 'character', 'actions'];
  @ViewChild(MatTable) table!: MatTable<any>;
  control: FormControl = new FormControl();
  optionSelected(event: MatAutocompleteSelectedEvent) {
    if (
      this.selectedActors.filter((value) => {
        return value.name == event.option.value.name;
      }).length == 0
    ) {
      this.selectedActors.push(event.option.value);
    }

    this.control.patchValue('');
    if (this.table !== undefined) {
      this.table.renderRows();
    }
  }
  remove(actor) {
    const index = this.selectedActors.findIndex((a) => a.name === actor.name);
    this.selectedActors.splice(index, 1);
    this.table.renderRows();
  }
  dropped(event: CdkDragDrop<any[]>) {
    const previousIndex = this.selectedActors.findIndex(
      (actor) => actor === event.item.data
    );
    moveItemInArray(this.selectedActors, previousIndex, event.currentIndex);
    this.table.renderRows();
  }
}
