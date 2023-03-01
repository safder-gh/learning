import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {
  movieTheaterCreationDTO,
  movieTheaterDTO,
} from '../movie-theaters.model';

@Component({
  selector: 'app-edit-movie-theater',
  templateUrl: './edit-movie-theater.component.html',
  styleUrls: ['./edit-movie-theater.component.css'],
})
export class EditMovieTheaterComponent {
  model: movieTheaterDTO = {
    name: 'Rex Audetorium',
    latitude: 24.407137917727667,
    longitude: 67.06054687500001,
  };
  saveChanges(movieTheater: movieTheaterCreationDTO) {
    console.log(movieTheater);
  }
}
