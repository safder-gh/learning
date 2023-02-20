import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { genreCreationDTO } from '../genres.model';

@Component({
  selector: 'app-edit-genre',
  templateUrl: './edit-genre.component.html',
  styleUrls: ['./edit-genre.component.css']
})
export class EditGenreComponent {
  constructor(private router:Router){}
  model:genreCreationDTO={name:'Drama'}
  saveChanges(genreCreationDTO:genreCreationDTO){
    console.log(genreCreationDTO)
    this.router.navigate(['/genres'])
    }
}
