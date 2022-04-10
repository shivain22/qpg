import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDifficultyTypeMaster } from 'app/shared/model/difficulty-type-master.model';

type EntityResponseType = HttpResponse<IDifficultyTypeMaster>;
type EntityArrayResponseType = HttpResponse<IDifficultyTypeMaster[]>;

@Injectable({ providedIn: 'root' })
export class DifficultyTypeMasterService {
  public resourceUrl = SERVER_API_URL + 'api/difficulty-type-masters';

  constructor(protected http: HttpClient) {}

  create(difficultyTypeMaster: IDifficultyTypeMaster): Observable<EntityResponseType> {
    return this.http.post<IDifficultyTypeMaster>(this.resourceUrl, difficultyTypeMaster, { observe: 'response' });
  }

  update(difficultyTypeMaster: IDifficultyTypeMaster): Observable<EntityResponseType> {
    return this.http.put<IDifficultyTypeMaster>(this.resourceUrl, difficultyTypeMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDifficultyTypeMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDifficultyTypeMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
