import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {
  movieTheaterCreationDTO,
  movieTheaterDTO,
} from '../movie-theaters.model';
import { coordinatesMap } from '../../utilities/map/coordinate';

@Component({
  selector: 'app-movie-theater-from',
  templateUrl: './movie-theater-from.component.html',
  styleUrls: ['./movie-theater-from.component.css'],
})
export class MovieTheaterFromComponent implements OnInit {
  constructor(private formBuilder: FormBuilder) {}
  @Input()
  model!: movieTheaterDTO;
  @Output()
  onSaveChanges = new EventEmitter<movieTheaterCreationDTO>();

  initialCoordinate: coordinatesMap[] = [];

  form!: FormGroup;
  ngOnInit(): void {
    this.form = this.formBuilder.group({
      name: [
        '',
        {
          validators: [Validators.required],
        },
      ],
      latitude: [
        '',
        {
          validators: [Validators.required],
        },
      ],
      longitude: [
        '',
        {
          validators: [Validators.required],
        },
      ],
    });
    if (this.model !== undefined) {
      this.form.patchValue(this.model);
    }
    this.initialCoordinate.push({
      latitude: this.model.latitude,
      longitude: this.model.longitude,
    });
  }
  onSelectedLocation(coordinates: coordinatesMap) {
    this.form.patchValue(coordinates);
  }
  saveChanges() {
    this.onSaveChanges.emit(this.form.value);
  }
}
