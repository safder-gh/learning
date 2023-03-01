import { Injectable } from '@angular/core';
import { genreDTO, genreCreationDTO } from './genres.model';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class GenresService {
  constructor(private http: HttpClient) {}
  private apiURL = environment.apiURL + 'genres';
  getAll(): Observable<genreDTO[]> {
    return this.http.get<genreDTO[]>(this.apiURL);
  }
  getById(id: number): Observable<genreDTO> {
    return this.http.get<genreDTO>(`${this.apiURL}/${id}`);
  }
  create(genre: genreCreationDTO) {
    return this.http.post(this.apiURL, genre);
  }
  edit(id: number, genre: genreCreationDTO) {
    return this.http.put<genreDTO>(`${this.apiURL}/${id}`, genre);
  }
}
