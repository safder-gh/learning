import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { firstLetterUppercase } from '../../validators/firstLetterUppercase';
import { genreCreationDTO } from '../genres.model';

@Component({
  selector: 'app-form-genre',
  templateUrl: './form-genre.component.html',
  styleUrls: ['./form-genre.component.css'],
})
export class FormGenreComponent {
  constructor(private formBuilder: FormBuilder) {}
  ngOnInit(): void {
    this.form = this.formBuilder.group({
      name: [
        '',
        {
          validators: [
            Validators.required,
            Validators.minLength(3),
            firstLetterUppercase(),
          ],
        },
      ],
    });
    if (this.model !== undefined) {
      this.form.patchValue(this.model);
    }
  }
  form!: FormGroup;
  @Input()
  model!: genreCreationDTO;
  @Output()
  onSaveChanges: EventEmitter<genreCreationDTO> =
    new EventEmitter<genreCreationDTO>();
  saveChanges() {
    this.onSaveChanges.emit(this.form.value);
  }
  getErrorMessageFieldName() {
    const field = this.form.get('name');
    if (field && field.hasError('required')) {
      return 'The name field is required!';
    } else if (field && field.hasError('minlength')) {
      return 'Minimum length is 3!';
    }
    if (field && field.hasError('firstLetterUppercase')) {
      return field.getError('firstLetterUppercase').message;
    }
    return '';
  }
}
