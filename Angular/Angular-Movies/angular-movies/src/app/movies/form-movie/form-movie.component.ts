import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { movieCreationDTO, movieDTO } from '../movies.model';
import { multipleSelectorModel } from '../../utilities/multiple-selector/multiple-selector.model';

@Component({
  selector: 'app-form-movie',
  templateUrl: './form-movie.component.html',
  styleUrls: ['./form-movie.component.css'],
})
export class FormMovieComponent implements OnInit {
  constructor(private formBuilder: FormBuilder) {
    this.model = {
      title: '',
      summary: '',
      poster: '',
      trailer: '',
      inTheaters: false,
      releaseDate: new Date(),
    };
  }
  form!: FormGroup;
  @Input()
  model!: movieDTO;
  @Output()
  onSaveChanges = new EventEmitter<movieCreationDTO>();
  NonSelectedGenres: multipleSelectorModel[] = [
    { key: 1, value: 'Drama' },
    { key: 2, value: 'Action' },
    { key: 3, value: 'Comedy' },
  ];

  SelectedGenres: multipleSelectorModel[] = [];

  SelectedMovieTheaters: multipleSelectorModel[] = [];
  NonSelectedMovieTheaters: multipleSelectorModel[] = [
    { key: 1, value: 'Rex' },
    { key: 2, value: 'Agora' },
    { key: 3, value: 'Capri' },
  ];
  ngOnInit(): void {
    this.form = this.formBuilder.group({
      title: [
        '',
        {
          validators: [Validators.required],
        },
      ],
      summary: '',
      inTheaters: false,
      trailer: '',
      releaseDate: '',
      poster: '',
      genresIds: '',
      movieTheatersIds: '',
    });
    if (this.model !== undefined) {
      this.form.patchValue(this.model);
    }
  }
  saveChanges() {
    const genresIds = this.SelectedGenres.map((value) => value.key);
    this.form.get('genresIds')!.setValue(genresIds);

    const movieTheatersIds = this.SelectedMovieTheaters.map(
      (value) => value.key
    );
    this.form.get('movieTheatersIds')!.setValue(movieTheatersIds);

    this.onSaveChanges.emit(this.form.value);
  }
  changeMarkdown(content: string) {
    this.form.get('summary')!.setValue(content);
  }
  onImageSelected(file: File) {
    this.form.get('poster')!.setValue(file);
  }
}
