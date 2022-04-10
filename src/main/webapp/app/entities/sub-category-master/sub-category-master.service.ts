import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISubCategoryMaster } from 'app/shared/model/sub-category-master.model';

type EntityResponseType = HttpResponse<ISubCategoryMaster>;
type EntityArrayResponseType = HttpResponse<ISubCategoryMaster[]>;

@Injectable({ providedIn: 'root' })
export class SubCategoryMasterService {
  public resourceUrl = SERVER_API_URL + 'api/sub-category-masters';

  constructor(protected http: HttpClient) {}

  create(subCategoryMaster: ISubCategoryMaster): Observable<EntityResponseType> {
    return this.http.post<ISubCategoryMaster>(this.resourceUrl, subCategoryMaster, { observe: 'response' });
  }

  update(subCategoryMaster: ISubCategoryMaster): Observable<EntityResponseType> {
    return this.http.put<ISubCategoryMaster>(this.resourceUrl, subCategoryMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubCategoryMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubCategoryMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
