import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Person } from "./person.model";
import { environment } from './../../environments/environment';


@Injectable({ providedIn: 'root' })
export class PersonService {
  apiUrl = environment.apiUrl

  constructor(
    private http: HttpClient,
  ) { }

  getPeople(): Observable<Person[]> {
    return this.http.get<Person[]>(this.apiUrl)
      .pipe(
        tap(_ => console.log('fetched people')),
      )
  }

  getPerson(id: string): Observable<Person> {
    const url = `${this.apiUrl}/${id}`

    return this.http.get<Person>(url)
      .pipe(
        tap(_ => console.log(`fetched person with id ${id}`)),
      )
  }

  deletePerson(id: string): Observable<Person> {
    const url = `${this.apiUrl}/${id}`

    return this.http.delete<Person>(url)
      .pipe(
        tap(_ => console.log(`deleted person with id ${id}`)),
      )
  }

  updatePerson(person: Person): Observable<any> {
    const url = `${this.apiUrl}/${person.id}`

    return this.http.patch(url, person)
      .pipe(
        tap(_ => console.log(`updated person with id ${person.id}`)),
      );
  }

  createPerson(person: Person): Observable<any> {
    return this.http.post(this.apiUrl, person)
      .pipe(
        tap(_ => console.log(`created person`)),
      );
  }

}
