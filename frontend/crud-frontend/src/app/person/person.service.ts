import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Person } from "./person.model";
import { environment } from './../../environments/environment';


@Injectable({ providedIn: 'root' })
export class PersonService {
  baseUrl = `${environment.apiUrl}/people`

  constructor(
    private http: HttpClient,
  ) { }

  getPeople(): Observable<Person[]> {
    return this.http.get<Person[]>(this.baseUrl)
      .pipe(
        tap(_ => console.log('fetched people')),
      )
  }

  getPerson(id: string): Observable<Person> {
    const url = `${this.baseUrl}/${id}`

    return this.http.get<Person>(url)
      .pipe(
        tap(_ => console.log(`fetched person with id ${id}`)),
      )
  }

  deletePerson(id: string): Observable<Person> {
    const url = `${this.baseUrl}/${id}`

    return this.http.delete<Person>(url)
      .pipe(
        tap(_ => console.log(`deleted person with id ${id}`)),
      )
  }

  updatePerson(person: Person): Observable<any> {
    const url = `${this.baseUrl}/${person.id}`

    return this.http.patch(url, person)
      .pipe(
        tap(_ => console.log(`updated person with id ${person.id}`)),
      );
  }

  createPerson(person: Person): Observable<any> {
    return this.http.post(this.baseUrl, person)
      .pipe(
        tap(_ => console.log(`created person`)),
      );
  }

}
