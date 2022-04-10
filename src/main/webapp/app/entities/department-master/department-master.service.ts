import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDepartmentMaster } from 'app/shared/model/department-master.model';

type EntityResponseType = HttpResponse<IDepartmentMaster>;
type EntityArrayResponseType = HttpResponse<IDepartmentMaster[]>;

@Injectable({ providedIn: 'root' })
export class DepartmentMasterService {
  public resourceUrl = SERVER_API_URL + 'api/department-masters';

  constructor(protected http: HttpClient) {}

  create(departmentMaster: IDepartmentMaster): Observable<EntityResponseType> {
    return this.http.post<IDepartmentMaster>(this.resourceUrl, departmentMaster, { observe: 'response' });
  }

  update(departmentMaster: IDepartmentMaster): Observable<EntityResponseType> {
    return this.http.put<IDepartmentMaster>(this.resourceUrl, departmentMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDepartmentMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDepartmentMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
