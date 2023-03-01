import { Component, OnInit } from '@angular/core';
import { GenresService } from '../genres.service';
import { genreDTO } from '../genres.model';

@Component({
  selector: 'app-index-genres',
  templateUrl: './index-genres.component.html',
  styleUrls: ['./index-genres.component.css'],
})
export class IndexGenresComponent implements OnInit {
  genres: genreDTO[] = [];
  columnsToDisplay = ['name', 'action'];
  constructor(private genresService: GenresService) {}
  ngOnInit(): void {
    this.genresService.getAll().subscribe((genres) => {
      this.genres = genres;
      console.log(this.genres);
    });
  }
}
