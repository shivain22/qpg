import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { QpBankUploadMasterUpdateComponent } from 'app/entities/qp-bank-upload-master/qp-bank-upload-master-update.component';
import { QpBankUploadMasterService } from 'app/entities/qp-bank-upload-master/qp-bank-upload-master.service';
import { QpBankUploadMaster } from 'app/shared/model/qp-bank-upload-master.model';

describe('Component Tests', () => {
  describe('QpBankUploadMaster Management Update Component', () => {
    let comp: QpBankUploadMasterUpdateComponent;
    let fixture: ComponentFixture<QpBankUploadMasterUpdateComponent>;
    let service: QpBankUploadMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [QpBankUploadMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QpBankUploadMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QpBankUploadMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QpBankUploadMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QpBankUploadMaster(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new QpBankUploadMaster();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
