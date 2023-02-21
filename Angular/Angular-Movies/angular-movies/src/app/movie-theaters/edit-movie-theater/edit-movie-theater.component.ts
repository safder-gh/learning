import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { movieTheaterCreationDTO } from '../movie-theaters.model';

@Component({
  selector: 'app-edit-movie-theater',
  templateUrl: './edit-movie-theater.component.html',
  styleUrls: ['./edit-movie-theater.component.css'],
})
export class EditMovieTheaterComponent {
  model: movieTheaterCreationDTO = {
    name: 'Rex Audetorium',
  };
  saveChanges(movieTheater: movieTheaterCreationDTO) {
    console.log(movieTheater);
  }
}
