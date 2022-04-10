import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { QbMasterUpdateComponent } from 'app/entities/qb-master/qb-master-update.component';
import { QbMasterService } from 'app/entities/qb-master/qb-master.service';
import { QbMaster } from 'app/shared/model/qb-master.model';

describe('Component Tests', () => {
  describe('QbMaster Management Update Component', () => {
    let comp: QbMasterUpdateComponent;
    let fixture: ComponentFixture<QbMasterUpdateComponent>;
    let service: QbMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [QbMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QbMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QbMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QbMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QbMaster(123);
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
        const entity = new QbMaster();
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
