import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExamMaster } from 'app/shared/model/exam-master.model';

type EntityResponseType = HttpResponse<IExamMaster>;
type EntityArrayResponseType = HttpResponse<IExamMaster[]>;

@Injectable({ providedIn: 'root' })
export class ExamMasterService {
  public resourceUrl = SERVER_API_URL + 'api/exam-masters';

  constructor(protected http: HttpClient) {}

  create(examMaster: IExamMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(examMaster);
    return this.http
      .post<IExamMaster>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(examMaster: IExamMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(examMaster);
    return this.http
      .put<IExamMaster>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  generateQuestionPaper(examMaster: IExamMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(examMaster);
    return this.http
      .put<IExamMaster>(`${this.resourceUrl}/${examMaster.id}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExamMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExamMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(examMaster: IExamMaster): IExamMaster {
    const copy: IExamMaster = Object.assign({}, examMaster, {
      startDate: examMaster.startDate && examMaster.startDate.isValid() ? examMaster.startDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((examMaster: IExamMaster) => {
        examMaster.startDate = examMaster.startDate ? moment(examMaster.startDate) : undefined;
      });
    }
    return res;
  }
}
