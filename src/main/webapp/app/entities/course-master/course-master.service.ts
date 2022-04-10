import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICourseMaster } from 'app/shared/model/course-master.model';

type EntityResponseType = HttpResponse<ICourseMaster>;
type EntityArrayResponseType = HttpResponse<ICourseMaster[]>;

@Injectable({ providedIn: 'root' })
export class CourseMasterService {
  public resourceUrl = SERVER_API_URL + 'api/course-masters';

  constructor(protected http: HttpClient) {}

  create(courseMaster: ICourseMaster): Observable<EntityResponseType> {
    return this.http.post<ICourseMaster>(this.resourceUrl, courseMaster, { observe: 'response' });
  }

  update(courseMaster: ICourseMaster): Observable<EntityResponseType> {
    return this.http.put<ICourseMaster>(this.resourceUrl, courseMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICourseMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICourseMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
